TYPE=VIEW
query=select `u`.`username` AS `username`,`u`.`password` AS `password`,`r`.`role_name` AS `role_name` from ((`wsssoapboxdb`.`users_roles` `ur` join `wsssoapboxdb`.`users` `u` on((`u`.`USER_ID` = `ur`.`user_id`))) join `wsssoapboxdb`.`roles` `r` on((`r`.`role_id` = `ur`.`role_id`)))
md5=f5bc56d8c9cadd4e143512f7e779dfc9
updatable=1
algorithm=0
definer_user=root
definer_host=localhost
suid=2
with_check_option=0
timestamp=2012-01-30 15:17:02
create-version=1
source=SELECT  u.username, u.password, r.role_name\nFROM users_roles ur \nINNER JOIN users u ON u.user_id = ur.user_id \nINNER JOIN roles r ON r.role_id = ur.role_id
client_cs_name=utf8mb4
connection_cl_name=utf8mb4_general_ci
view_body_utf8=select `u`.`username` AS `username`,`u`.`password` AS `password`,`r`.`role_name` AS `role_name` from ((`wsssoapboxdb`.`users_roles` `ur` join `wsssoapboxdb`.`users` `u` on((`u`.`USER_ID` = `ur`.`user_id`))) join `wsssoapboxdb`.`roles` `r` on((`r`.`role_id` = `ur`.`role_id`)))
