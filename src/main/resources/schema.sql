CREATE TABLE IF NOT EXISTS employees (
    employee_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(120) NOT NULL,
    designation VARCHAR(120) NOT NULL,
    base_salary DECIMAL(12,2) NOT NULL,
    role VARCHAR(20) NOT NULL,
    last_promotion_date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS performance_reviews (
    review_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_id BIGINT NOT NULL,
    review_year INT NOT NULL,
    task_completion INT NOT NULL,
    attendance INT NOT NULL,
    team_collaboration INT NOT NULL,
    problem_solving INT NOT NULL,
    communication INT NOT NULL,
    leadership INT NOT NULL,
    client_satisfaction INT NOT NULL,
    total_kpi_score INT NOT NULL,
    category VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_reviews_employee FOREIGN KEY (employee_id) REFERENCES employees(employee_id),
    CONSTRAINT uq_review_employee_year UNIQUE (employee_id, review_year)
);

CREATE TABLE IF NOT EXISTS bonus_records (
    bonus_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_id BIGINT NOT NULL,
    review_year INT NOT NULL,
    total_kpi_score INT NOT NULL,
    category VARCHAR(20) NOT NULL,
    bonus_percentage INT NOT NULL,
    bonus_amount DECIMAL(12,2) NOT NULL,
    total_compensation DECIMAL(12,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_bonus_employee FOREIGN KEY (employee_id) REFERENCES employees(employee_id),
    CONSTRAINT uq_bonus_employee_year UNIQUE (employee_id, review_year)
);

CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_id BIGINT NOT NULL,
    username VARCHAR(80) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,
    CONSTRAINT fk_users_employee FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);

CREATE TABLE IF NOT EXISTS audit_logs (
    log_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_id BIGINT NOT NULL,
    action VARCHAR(160) NOT NULL,
    performed_by VARCHAR(80) NOT NULL,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_audit_employee FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);

INSERT INTO employees (employee_id, name, designation, base_salary, role, last_promotion_date)
SELECT 1, 'Ayesha Rahman', 'Operations Associate', 65000.00, 'EMPLOYEE', '2024-02-01'
WHERE NOT EXISTS (SELECT 1 FROM employees WHERE employee_id = 1);

INSERT INTO employees (employee_id, name, designation, base_salary, role, last_promotion_date)
SELECT 2, 'Marcus Chen', 'Logistics Manager', 95000.00, 'MANAGER', '2023-09-15'
WHERE NOT EXISTS (SELECT 1 FROM employees WHERE employee_id = 2);

INSERT INTO employees (employee_id, name, designation, base_salary, role, last_promotion_date)
SELECT 3, 'Nadia Alvarez', 'HR Administrator', 110000.00, 'ADMIN', '2022-06-20'
WHERE NOT EXISTS (SELECT 1 FROM employees WHERE employee_id = 3);

INSERT INTO users (employee_id, username, password, role)
SELECT 1, 'employee', '$2a$10$7EqJtq98hPqEX7fNZaFWoOhi5ZdT.Ww.2K5rWtUL7ex4gzaKNCJ9W', 'EMPLOYEE'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'employee');

INSERT INTO users (employee_id, username, password, role)
SELECT 2, 'manager', '$2a$10$7EqJtq98hPqEX7fNZaFWoOhi5ZdT.Ww.2K5rWtUL7ex4gzaKNCJ9W', 'MANAGER'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'manager');

INSERT INTO users (employee_id, username, password, role)
SELECT 3, 'admin', '$2a$10$7EqJtq98hPqEX7fNZaFWoOhi5ZdT.Ww.2K5rWtUL7ex4gzaKNCJ9W', 'ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');
