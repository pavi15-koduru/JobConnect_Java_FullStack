CREATE TABLE IF NOT EXISTS jobs (
 id BIGINT PRIMARY KEY AUTO_INCREMENT, title VARCHAR(120) NOT NULL,
 company VARCHAR(120) NOT NULL, location VARCHAR(120) NOT NULL,
 skills VARCHAR(255) NOT NULL, description VARCHAR(1000) NOT NULL
);
CREATE TABLE IF NOT EXISTS applications (
 id BIGINT PRIMARY KEY AUTO_INCREMENT, job_id BIGINT NOT NULL,
 applicant_name VARCHAR(120) NOT NULL, email VARCHAR(180) NOT NULL,
 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 CONSTRAINT fk_applications_job FOREIGN KEY (job_id) REFERENCES jobs(id)
);
INSERT INTO jobs (title,company,location,skills,description)
SELECT 'Junior Java Developer','TechNova','Hyderabad','Java, Spring Boot, SQL','Work on backend services, REST APIs, and database integration.'
WHERE NOT EXISTS (SELECT 1 FROM jobs WHERE title='Junior Java Developer' AND company='TechNova');
INSERT INTO jobs (title,company,location,skills,description)
SELECT 'Full Stack Developer Intern','CodeBridge','Bengaluru','Java, JavaScript, HTML, CSS','Build web features and connect frontend pages to backend APIs.'
WHERE NOT EXISTS (SELECT 1 FROM jobs WHERE title='Full Stack Developer Intern' AND company='CodeBridge');
INSERT INTO jobs (title,company,location,skills,description)
SELECT 'Backend Developer Trainee','CloudSprint','Chennai','Java, REST APIs, MySQL','Assist with backend endpoints, data storage, and API testing.'
WHERE NOT EXISTS (SELECT 1 FROM jobs WHERE title='Backend Developer Trainee' AND company='CloudSprint');
