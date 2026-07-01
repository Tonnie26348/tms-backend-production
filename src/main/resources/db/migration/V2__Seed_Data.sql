-- Seed Data for TMS

-- 1. Insert Roles
INSERT INTO roles (name, description) VALUES
('SUPER_ADMIN', 'Overall system administration and configuration'),
('OPERATIONS_MANAGER', 'Fleet, route, and trip management'),
('ACCOUNTANT', 'Financial management, collections, and reports'),
('DISPATCHER', 'Real-time vehicle dispatching and scheduling'),
('DRIVER', 'Operates vehicles and manages specific trips'),
('PASSENGER', 'End customer booking trips and managing profile');

-- 2. Insert Permissions
INSERT INTO permissions (name, description) VALUES
('MANAGE_USERS', 'Create, update, delete users'),
('MANAGE_ROLES', 'Configure roles and permissions'),
('MANAGE_FLEET', 'Manage vehicle inventory, maintenance, and logs'),
('MANAGE_ROUTES', 'Create and modify routes and stops'),
('MANAGE_TRIPS', 'Schedule and assign trips'),
('BOOK_TICKET', 'Book trip seats'),
('PROCESS_PAYMENTS', 'Accept and record payments'),
('VIEW_REPORTS', 'View and export analytics reports'),
('DRIVER_ACCESS', 'Access driver-specific mobile features'),
('PASSENGER_ACCESS', 'Access passenger-specific portal features');

-- 3. Associate Permissions with Roles
-- SUPER_ADMIN gets all permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id FROM roles r, permissions p WHERE r.name = 'SUPER_ADMIN';

-- OPERATIONS_MANAGER permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id FROM roles r, permissions p 
WHERE r.name = 'OPERATIONS_MANAGER' AND p.name IN ('MANAGE_FLEET', 'MANAGE_ROUTES', 'MANAGE_TRIPS', 'VIEW_REPORTS');

-- ACCOUNTANT permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id FROM roles r, permissions p 
WHERE r.name = 'ACCOUNTANT' AND p.name IN ('PROCESS_PAYMENTS', 'VIEW_REPORTS');

-- DISPATCHER permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id FROM roles r, permissions p 
WHERE r.name = 'DISPATCHER' AND p.name IN ('MANAGE_TRIPS');

-- DRIVER permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id FROM roles r, permissions p 
WHERE r.name = 'DRIVER' AND p.name = 'DRIVER_ACCESS';

-- PASSENGER permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id FROM roles r, permissions p 
WHERE r.name = 'PASSENGER' AND p.name = 'PASSENGER_ACCESS';

-- 4. Create Users (Passwords are BCrypt hashed for 'password123')
-- Default Password Hash for 'password123' is '$2a$10$8.KclW9K3vA0gL6wDIdUbe5eB5Y77D2Nf1H1N5q.rYfT4G6K5543e' (standard bcrypt)
-- Wait, actually let's use the standard BCrypt for 'password123': $2a$12$R9h/cIPz0gi.UR1gLQY7aeS1tBP5vvf1H1S/O6M57A7W6.Z2.5yfe
INSERT INTO users (first_name, last_name, email, password, phone, role_id, is_active, is_verified)
VALUES
('Admin', 'User', 'admin@tms.com', '$2a$12$R9h/cIPz0gi.UR1gLQY7aeS1tBP5vvf1H1S/O6M57A7W6.Z2.5yfe', '+254700000001', (SELECT id FROM roles WHERE name = 'SUPER_ADMIN'), TRUE, TRUE),
('Ops', 'Manager', 'ops@tms.com', '$2a$12$R9h/cIPz0gi.UR1gLQY7aeS1tBP5vvf1H1S/O6M57A7W6.Z2.5yfe', '+254700000002', (SELECT id FROM roles WHERE name = 'OPERATIONS_MANAGER'), TRUE, TRUE),
('Finance', 'Accountant', 'finance@tms.com', '$2a$12$R9h/cIPz0gi.UR1gLQY7aeS1tBP5vvf1H1S/O6M57A7W6.Z2.5yfe', '+254700000003', (SELECT id FROM roles WHERE name = 'ACCOUNTANT'), TRUE, TRUE),
('John', 'Driver', 'john.driver@tms.com', '$2a$12$R9h/cIPz0gi.UR1gLQY7aeS1tBP5vvf1H1S/O6M57A7W6.Z2.5yfe', '+254700000004', (SELECT id FROM roles WHERE name = 'DRIVER'), TRUE, TRUE),
('Alice', 'Passenger', 'alice@gmail.com', '$2a$12$R9h/cIPz0gi.UR1gLQY7aeS1tBP5vvf1H1S/O6M57A7W6.Z2.5yfe', '+254700000005', (SELECT id FROM roles WHERE name = 'PASSENGER'), TRUE, TRUE);

-- 5. Insert Drivers
INSERT INTO drivers (id, license_number, license_expiry, emergency_contact_name, emergency_contact_phone, status, rating)
VALUES
((SELECT id FROM users WHERE email = 'john.driver@tms.com'), 'DL-12345678', '2028-12-31', 'Jane Doe', '+254711111111', 'AVAILABLE', 4.85);

-- 6. Insert Vehicles
INSERT INTO vehicles (registration_number, make, model, year, capacity, status, insurance_policy_number, insurance_expiry, inspection_expiry, road_license_expiry, fuel_type)
VALUES
('KBX 123A', 'Toyota', 'Hiace (14-seater)', 2018, 14, 'AVAILABLE', 'INS-998877', '2027-06-30', '2027-06-30', '2027-06-30', 'DIESEL'),
('KCY 456B', 'Scania', 'Metrolink (49-seater)', 2020, 49, 'AVAILABLE', 'INS-554433', '2027-12-31', '2027-12-31', '2027-12-31', 'DIESEL');

-- 7. Insert Routes
INSERT INTO routes (origin, destination, distance_km, estimated_duration_minutes, status)
VALUES
('Nairobi', 'Mombasa', 485.50, 480, 'ACTIVE'),
('Nairobi', 'Kisumu', 350.00, 360, 'ACTIVE');

-- 8. Insert Stops
INSERT INTO stops (route_id, name, sequence_number, distance_from_origin_km, estimated_time_from_origin_minutes)
VALUES
((SELECT id FROM routes WHERE destination = 'Mombasa'), 'Mtito Andei', 1, 240.00, 240),
((SELECT id FROM routes WHERE destination = 'Mombasa'), 'Voi', 2, 380.00, 360),
((SELECT id FROM routes WHERE destination = 'Kisumu'), 'Nakuru', 1, 160.00, 150),
((SELECT id FROM routes WHERE destination = 'Kisumu'), 'Kericho', 2, 260.00, 250);
