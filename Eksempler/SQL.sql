DROP database SemesterProjekt2;
CREATE DATABASE SemesterProjekt2;
Use SemesterProjekt2;

CREATE TABLE PatientListe(
PatientID INT,
CPR VARCHAR(10),
PatientName VARCHAR(255),
PRIMARY KEY(PatientID)
);

CREATE TABLE EKGMeasurement(
MeasurementID int,
CPR VARCHAR(10),
Measurement INT,
PRIMARY KEY(MeasurementID),
foreign key(CPR) REFERENCES PatientListe(CPR)
);