INSERT INTO tt_job (id, "name") VALUES ('82a8ed50-906b-11ee-b9d1-0242ac120003', 'PRIME_JOB');
INSERT INTO tt_user (id, "name", job_id) VALUES ('82a8ed50-906b-11ee-b9d1-0242ac120002', 'PRIME_USER', '82a8ed50-906b-11ee-b9d1-0242ac120003');
INSERT INTO tt_user_auth (id, login, manager_role, password, user_id) VALUES (gen_random_uuid(), 'PRIME_LOGIN', true, '$2a$10$y4JNAQ9Av2TT64TsfJWOku6ROa4Z3JUVL3wHInfoyPH1./wrPStka','82a8ed50-906b-11ee-b9d1-0242ac120002');

INSERT INTO tt_project (id, name, is_active) VALUES ('82a8ed50-906b-11ee-b9d1-0242ac120004', 'Простой', true);
INSERT INTO tt_user_project (project_id, user_id) VALUES ('82a8ed50-906b-11ee-b9d1-0242ac120004', '82a8ed50-906b-11ee-b9d1-0242ac120002');