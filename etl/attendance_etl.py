from pyspark.sql import SparkSession
from pyspark.sql.functions import *
from pyspark.sql.window import Window

# Step 1: Start Spark Session
spark = SparkSession.builder.appName("AttendanceDurationCheck").getOrCreate()

# Step 2: Load tables (replace with actual JDBC config or paths)
attendance_df = spark.read.format("jdbc")\
    .option("url", "jdbc:postgresql://localhost:5432/yourdb")\
    .option("dbtable", "attendanceRecord")\
    .option("user", "postgres")\
    .option("password", "password")\
    .load()

students_df = spark.read.format("jdbc")\
    .option("url", "jdbc:postgresql://localhost:5432/yourdb")\
    .option("dbtable", "students")\
    .option("user", "postgres")\
    .option("password", "password")\
    .load()

subjects_df = spark.read.format("jdbc")\
    .option("url", "jdbc:postgresql://localhost:5432/yourdb")\
    .option("dbtable", "subjects")\
    .option("user", "postgres")\
    .option("password", "password")\
    .load()

# Step 3: Compute duration using window functions
win = Window.partitionBy("student_id", "subject_id", "date")

attendance_df = attendance_df.withColumn("in_time", min("timestamp").over(win)) \
                             .withColumn("out_time", max("timestamp").over(win)) \
                             .dropDuplicates(["student_id", "subject_id", "date"]) \
                             .withColumn("duration_min", (col("out_time").cast("long") - col("in_time").cast("long")) / 60) \
                             .withColumn("present", when(col("duration_min") > 40, True).otherwise(False)) \
                             .withColumn("month", month("date")) \
                             .withColumn("year", year("date"))

# Step 4: Monthly Aggregation
monthly_df = attendance_df.groupBy("student_id", "subject_id", "year", "month") \
    .agg(
        count(when(col("present") == True, True)).alias("days_present"),
        count("*").alias("total_days"),
        collect_list(when(col("present") == False, col("date"))).alias("absent_dates")
    ) \
    .withColumn("attendance_percentage", round((col("days_present") / col("total_days")) * 100, 2))

# Step 5: Join with student and subject names
monthly_df = monthly_df \
    .join(students_df.select("student_id", "name").withColumnRenamed("name", "student_name"), on="student_id", how="left") \
    .join(subjects_df.select("subject_id", "name").withColumnRenamed("name", "subject_name"), on="subject_id", how="left")

# Step 6: Write CSV files per student and subject
output_path = "/path/to/output"

students_rows = monthly_df.select("student_id").distinct().collect()
students = [row["student_id"] for row in students_rows]

subjects_rows = monthly_df.select("subject_id").distinct().collect()
subjects = [row["subject_id"] for row in subjects_rows]

for student in students:
    for subject in subjects:
        report_df = monthly_df.filter((col("student_id") == student) & (col("subject_id") == subject))
        if report_df.count() > 0:
            filename = f"{output_path}/student_{student}_subject_{subject}.csv"
            report_df.select(
                "student_id", "student_name", 
                "subject_id", "subject_name",
                "year", "month", 
                "days_present", "total_days", 
                "attendance_percentage", "absent_dates"
            ).coalesce(1).write.mode("overwrite").option("header", True).csv(filename)
