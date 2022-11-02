-- Dumping data for table mifostenant-default.m_appuser: ~2 rows (approximately)
/*!40000 ALTER TABLE `m_appuser` DISABLE KEYS */;


-- Dumping data for table mifostenant-default.m_appuser_previous_password: ~0 rows (approximately)
/*!40000 ALTER TABLE `m_appuser_previous_password` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_appuser_previous_password` ENABLE KEYS */;

-- Dumping structure for table mifostenant-default.m_appuser_role

-- Dumping data for table mifostenant-default.m_appuser_role: ~1 rows (approximately)
/*!40000 ALTER TABLE `m_appuser_role` DISABLE KEYS */;

INSERT INTO `m_role` (`id`, `name`, `description`, `is_disabled`) VALUES
  (1, 'Super user', 'This role provides all application permissions.', 0);

INSERT INTO `m_role_permission` (`role_id`, `permission_id`) VALUES
  (1, 1);

  /*!40000 ALTER TABLE `m_appuser_role` ENABLE KEYS */;
INSERT INTO `m_appuser` (`id`, `is_deleted`, `office_id`, `staff_id`, `username`, `firstname`, `lastname`, `password`, `email`, `firsttime_login_remaining`, `nonexpired`, `nonlocked`, `nonexpired_credentials`, `enabled`, `last_time_password_updated`, `password_never_expires`, `is_self_service_user`) VALUES
  (1, 0, 1, NULL, 'wese', 'App', 'Administrator', '5787039480429368bf94732aacc771cd0a3ea02bcf504ffe1185ab94213bc63a', 'taatconsulting.com', b'0', b'1', b'1', b'1', b'1', '2017-07-28', 0, b'0'),
  (2, 0, 1, NULL, 'system', 'system', 'system', '5787039480429368bf94732aacc771cd0a3ea02bcf504ffe1185ab94213bc63a', 'taatconsulting.com', b'0', b'1', b'1', b'1', b'1', '2014-03-07', 0, b'0');
/*!40000 ALTER TABLE `m_appuser` ENABLE KEYS */;


INSERT INTO `m_appuser_role` (`appuser_id`, `role_id`) VALUES
  (1, 1);
/*!40000 ALTER TABLE `m_appuser_role` ENABLE KEYS */;