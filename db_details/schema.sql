CREATE TABLE students (
  student_id INT PRIMARY KEY,
  name TEXT,
  department_id INT,
  semester INT
);

CREATE TABLE subjects (
  subject_id INT PRIMARY KEY,
  name TEXT,
  semester INT,
  department_id INT
);

CREATE TABLE attendanceRecord (
  id SERIAL PRIMARY KEY,
  student_id INT REFERENCES students(student_id),
  subject_id INT REFERENCES subjects(subject_id),
  date DATE,
  present Boolean
);
