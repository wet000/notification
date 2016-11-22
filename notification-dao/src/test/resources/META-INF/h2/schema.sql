CREATE TABLE IF NOT EXISTS subscriber (
	id int IDENTITY PRIMARY KEY,
	form_id smallint,
	email varchar,
	active tinyint,
	confirmed tinyint,
	create_date timestamp,
	activate_date timestamp,
	deactivate_date timestamp,
	confirm_date timestamp,
	version timestamp,
	UNIQUE KEY form_email (form_id, email)
)