-- MySQL dump 10.13  Distrib 5.5.9, for Win32 (x86)
--
-- Host: localhost    Database: wsssoapboxdb
-- ------------------------------------------------------
-- Server version	5.5.8

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `wsssoapboxdb`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `wsssoapboxdb` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `wsssoapboxdb`;

--
-- Table structure for table `auditlog`
--

DROP TABLE IF EXISTS `auditlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auditlog` (
  `AUDIT_LOG_ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `ACTION` varchar(100) NOT NULL,
  `DETAIL` text NOT NULL,
  `CREATED_DATE` date NOT NULL,
  `ENTITY_ID` bigint(20) unsigned NOT NULL,
  `ENTITY_NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`AUDIT_LOG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auditlog`
--

LOCK TABLES `auditlog` WRITE;
/*!40000 ALTER TABLE `auditlog` DISABLE KEYS */;
/*!40000 ALTER TABLE `auditlog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `key_store`
--

DROP TABLE IF EXISTS `key_store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `key_store` (
  `keystore_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `alias_name` varchar(200) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `content` blob NOT NULL,
  `type` varchar(45) DEFAULT 'jks',
  `provider` varchar(50) DEFAULT 'SUN',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`keystore_id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `key_store`
--

LOCK TABLES `key_store` WRITE;
/*!40000 ALTER TABLE `key_store` DISABLE KEYS */;
INSERT INTO `key_store` VALUES (60,'client.jks','client_keystore','123456','����\0\0\0\0\0\0\0\0\0\0server_cert\0\04f�e�\0X.509\0\0k0�g0�РN�:&0\r	*�H��\r\00x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0\r111222140942Z\r120321140942Z0x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0��0\r	*�H��\r\0��\00����\0�~��!�,҃��\Z!\r�E�js۶\\#��}�\Z�ɝ\r��]�\nڹh����唗 h�T�N�\0�1@Bޖ�z��L u���Gr\n2�e�����)�t� kV�u��5��-Z$�J{ɼ�9f����/\00\r	*�H��\r\0��\02T����r�}R2&�����\"��CI�\Z�f��\n:�Y7���5��z(��T#6m�m��4�n�kןd�?�c\r1�n�,�p�}L}�t�P�!wN�X�;kI�I����qA\\��\0��\0\0\0\0client cert\0\05@��\0X.509\0\0�0�}0��N�:�0\r	*�H��\r\00��10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0\r111222141237Z\r120321141237Z0��10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0��0\r	*�H��\r\0��\00����\0�_�\n��y<}b�\\:蘮Dˏ���}\r؈�M7Z3��սSE�����KD�ak�T����H�w�>=f=H�۳�,��.�q�OV\n�U�e�J��U4U~���9�ude7�l�ᄅ}\ZS�y99i\00\r	*�H��\r\0��\0kW\Z�d�G1��I3�$���1aRps�ʔ�c��_��qɦ]�vu��W�^��(D��B�~u��E<�&��S����a�k��\n6�G��go8dϽB�v�@���(�\\��~I7�qA�#�p�$�\0\0\0\0client_keystore\0\04f�u\0\0�0��0\n+*\0��<��$&���j���խ�NT��ye�K�*i���W\\��i>�$���/M?+y���cue���n�)�g�(�V[�J\\����U����<��2y.����\\���l�}��$O��w���#��e����y<,�%����;�3��#�,��8�hZ���:���[Х �9W�_��>�lŧ�&�ޯ`Y$<U��3��y>�=t\0��2yw�����S4��t������2l���a<���[%�L;˪l�>=�ݠ�mAG4)+���V���E{#bl�$�����Z,�C�<�T\05��{w���w�HWm�]SVR�h��X^����y�P�m-vqt�!����Y�Y��~*�e�<7��v��4_�2ٿ��VG`$Ko���e�;	���[��y�tG�x���2�����d�!�L�7���IT�����H��I:}N�NkF��3��~dU{�1F!jK4xY\r�l�IJO�]�Of\0ZXӏ��\n�࣓��s�	�&p�wL�/�4ӭ��{��|���r�)�R���Rkx��@ϟR������ҏ	���m<��mB.	=ԝ�E�8lQ]�J������S,f6m)�1�}�zi�(�40�%���\"�l��w�5��#�-:��}�r���*�*��BNd�H�u�趐�w.�K�\'��|pf>��n�~%���g�֒\0\0\0\0X.509\0\0�0�}0��N�:�0\r	*�H��\r\00��10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0\r111222141237Z\r120321141237Z0��10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0��0\r	*�H��\r\0��\00����\0�_�\n��y<}b�\\:蘮Dˏ���}\r؈�M7Z3��սSE�����KD�ak�T����H�w�>=f=H�۳�,��.�q�OV\n�U�e�J��U4U~���9�ude7�l�ᄅ}\ZS�y99i\00\r	*�H��\r\0��\0kW\Z�d�G1��I3�$���1aRps�ʔ�c��_��qɦ]�vu��W�^��(D��B�~u��E<�&��S����a�k��\n6�G��go8dϽB�v�@���(�\\��~I7�qA�#�p�$���Q�\r\"У��z�[3R8D','JKS','SUN','2012-02-03 06:25:39',2),(61,'server.jks','server_keystore','123456','����\0\0\0\0\0\0\0\0\0\0client_cert\0\04f��>\0X.509\0\0�0�}0��N�:�0\r	*�H��\r\00��10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0\r111222141237Z\r120321141237Z0��10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0��0\r	*�H��\r\0��\00����\0�_�\n��y<}b�\\:蘮Dˏ���}\r؈�M7Z3��սSE�����KD�ak�T����H�w�>=f=H�۳�,��.�q�OV\n�U�e�J��U4U~���9�ude7�l�ᄅ}\ZS�y99i\00\r	*�H��\r\0��\0kW\Z�d�G1��I3�$���1aRps�ʔ�c��_��qɦ]�vu��W�^��(D��B�~u��E<�&��S����a�k��\n6�G��go8dϽB�v�@���(�\\��~I7�qA�#�p�$�\0\0\0\0server_keystore\0\04f[\0\0�0��0\n+*\0��>��笽�-�Z�z�T���m��_�J��Ǌ�먮�U�tp��~Ǎ�r�h�fF_y?o� ������\"�����S�W�(�Ǩ�Z]w\"\0�_�^�-o�%�,�M�ʤs�\\��f��e6Z-i����V��� ��)\0c�@���\ny��V7�7X�M�{�ב��GQ:P.��6�\0�X\n��W��x<��t/�F��T�4;x8���9�n�X��Wk�v�`�n\0�	S��Y�b��7\"�ɡ�Ozd�\"kgƎg|�2����}J�_B�Y�u�C�\r|�UU�����4��NrީՌ�-\"���I�����>:v(=��J�b��\"Q�{G���q�GaH���4�< y�T+�����R��?R�9`9�Ȟ���f�5(��PVĕ�f\nD���*���M@�ٝJ_u9g�\r/�G+S���\"21������f%�\n��1�`ӈ;l~b�Gi�_��+��r���j��	�D\Z8&hj$�[Ok����?�`�.��x���S�H�^2F��c\r�Ŋ��̉F�a�]^�{2���NPz[�ˏ0��b��l������\ZeĿ��b�p̺f�&!�O��T1��������Q(9#%/�D�;Բ�u�+�������^\Z\r���	��QwN�l�\0���j�]i;)�->h�_C#:,��(��\'3E��F\0\0\0\0X.509\0\0k0�g0�РN�:&0\r	*�H��\r\00x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0\r111222140942Z\r120321140942Z0x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0��0\r	*�H��\r\0��\00����\0�~��!�,҃��\Z!\r�E�js۶\\#��}�\Z�ɝ\r��]�\nڹh����唗 h�T�N�\0�1@Bޖ�z��L u���Gr\n2�e�����)�t� kV�u��5��-Z$�J{ɼ�9f����/\00\r	*�H��\r\0��\02T����r�}R2&�����\"��CI�\Z�f��\n:�Y7���5��z(��T#6m�m��4�n�kןd�?�c\r1�n�,�p�}L}�t�P�!wN�X�;kI�I����qA\\��\0����tb8R�z�u��C�FY ��','JKS','SUN','2012-02-03 22:54:59',2),(63,'server.jks','server_keystore','123456','����\0\0\0\0\0\0\0\0\0\0client_cert\0\04f��>\0X.509\0\0�0�}0��N�:�0\r	*�H��\r\00��10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0\r111222141237Z\r120321141237Z0��10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0��0\r	*�H��\r\0��\00����\0�_�\n��y<}b�\\:蘮Dˏ���}\r؈�M7Z3��սSE�����KD�ak�T����H�w�>=f=H�۳�,��.�q�OV\n�U�e�J��U4U~���9�ude7�l�ᄅ}\ZS�y99i\00\r	*�H��\r\0��\0kW\Z�d�G1��I3�$���1aRps�ʔ�c��_��qɦ]�vu��W�^��(D��B�~u��E<�&��S����a�k��\n6�G��go8dϽB�v�@���(�\\��~I7�qA�#�p�$�\0\0\0\0server_keystore\0\04f[\0\0�0��0\n+*\0��>��笽�-�Z�z�T���m��_�J��Ǌ�먮�U�tp��~Ǎ�r�h�fF_y?o� ������\"�����S�W�(�Ǩ�Z]w\"\0�_�^�-o�%�,�M�ʤs�\\��f��e6Z-i����V��� ��)\0c�@���\ny��V7�7X�M�{�ב��GQ:P.��6�\0�X\n��W��x<��t/�F��T�4;x8���9�n�X��Wk�v�`�n\0�	S��Y�b��7\"�ɡ�Ozd�\"kgƎg|�2����}J�_B�Y�u�C�\r|�UU�����4��NrީՌ�-\"���I�����>:v(=��J�b��\"Q�{G���q�GaH���4�< y�T+�����R��?R�9`9�Ȟ���f�5(��PVĕ�f\nD���*���M@�ٝJ_u9g�\r/�G+S���\"21������f%�\n��1�`ӈ;l~b�Gi�_��+��r���j��	�D\Z8&hj$�[Ok����?�`�.��x���S�H�^2F��c\r�Ŋ��̉F�a�]^�{2���NPz[�ˏ0��b��l������\ZeĿ��b�p̺f�&!�O��T1��������Q(9#%/�D�;Բ�u�+�������^\Z\r���	��QwN�l�\0���j�]i;)�->h�_C#:,��(��\'3E��F\0\0\0\0X.509\0\0k0�g0�РN�:&0\r	*�H��\r\00x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0\r111222140942Z\r120321140942Z0x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0��0\r	*�H��\r\0��\00����\0�~��!�,҃��\Z!\r�E�js۶\\#��}�\Z�ɝ\r��]�\nڹh����唗 h�T�N�\0�1@Bޖ�z��L u���Gr\n2�e�����)�t� kV�u��5��-Z$�J{ɼ�9f����/\00\r	*�H��\r\0��\02T����r�}R2&�����\"��CI�\Z�f��\n:�Y7���5��z(��T#6m�m��4�n�kןd�?�c\r1�n�,�p�}L}�t�P�!wN�X�;kI�I����qA\\��\0����tb8R�z�u��C�FY ��','JKS','SUN','2012-02-03 23:56:29',4),(64,'default.jks','default_keytore','123456','����\0\0\0\0\0\0\0\0\0\0client_cert\0\052���\0X.509\0\0�0�}0��N�:�0\r	*�H��\r\00��10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0\r111222141237Z\r120321141237Z0��10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0��0\r	*�H��\r\0��\00����\0�_�\n��y<}b�\\:蘮Dˏ���}\r؈�M7Z3��սSE�����KD�ak�T����H�w�>=f=H�۳�,��.�q�OV\n�U�e�J��U4U~���9�ude7�l�ᄅ}\ZS�y99i\00\r	*�H��\r\0��\0kW\Z�d�G1��I3�$���1aRps�ʔ�c��_��qɦ]�vu��W�^��(D��B�~u��E<�&��S����a�k��\n6�G��go8dϽB�v�@���(�\\��~I7�qA�#�p�$�\0\0\0\0server_cert\0\052���\0X.509\0\0k0�g0�РN�:&0\r	*�H��\r\00x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0\r111222140942Z\r120321140942Z0x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0��0\r	*�H��\r\0��\00����\0�~��!�,҃��\Z!\r�E�js۶\\#��}�\Z�ɝ\r��]�\nڹh����唗 h�T�N�\0�1@Bޖ�z��L u���Gr\n2�e�����)�t� kV�u��5��-Z$�J{ɼ�9f����/\00\r	*�H��\r\0��\02T����r�}R2&�����\"��CI�\Z�f��\n:�Y7���5��z(��T#6m�m��4�n�kןd�?�c\r1�n�,�p�}L}�t�P�!wN�X�;kI�I����qA\\��\0��\0\0\0\0default_keytore\0\052�#\0\00��0\n+*\0��9�81��`�~�#P�e_�B`�<����-U�ni=�����ɿ�6�2xY�6�W����HZ*[��wrb\n/��a�|�yb�Zl��cJ*4A���\n��N�Z%J)m	W?)���L AKJ�m�h�Q�����s1t�����\'��>����C��Q�Zlی�0,�1�\"R�9�{�Ht��\0�IFB��!;�����q.E�jκ<��/m�wSFC1.��|s��/&<UG��HHA#����i��<��ߠJY��ܔ)�1n�C������=W\'>�����aod�U܉�?\r��\0���j�&�3_�;�J:Ӫ�bye��ydAK!/�Ձ��l0M�ʹ�5$�z>������3p���:?���%K���=�M�m�nr[���U�~�F)�{ ���x$�).���n�Y���⌲�R��Y���Z��4�T�Ԑ\rt���,�;�¬\"\"�����f�\n���J�	r�����v�b�%z�V�$�W��|�-ؗ*���o��I1b�D�C�B�U%k�q�L>�T���\n�y����8|N,����Ց�kF {OtfKZ�r�[\r�U�X-�?j��;4��D�7No$d���Tn<t��Lf�-�Q���^��@�G.l��@���`U�1@K�b����E&m\n$E��J{��.�s5{<S�~ٜ~d�>��m��&i}�h��zxs�@����u&�I�L�R�����GC�B��n�S�6K����LX�\nr��\"E�\\�ΰ-�k��F�}�R����H����O8��@3���J�H��6p\Z��\nB�S��6\"����~x&��ELR8��4h6�{��N3[�ݺ\Z5y�\\�*�F��<�����35g�e\"��J�7��\0Ys�k�s���ZsN��n��D�\\E�1߾0��\n����Y(�j4�6ח��9�[nJ�r��~_�|�~�����g��Q|��.I�(7k#��m	g8d�ѢF����4���s\\�V\n�ݦ�B�6�o#J4��h�9�C�]�_2�@��!�Lס�-%� �\\��o��=��7��H#��)G/��a��X�X:�(7����8\'�5����qK[ϼ�p�2{a�;�r��9���q+����M֏\n-�j+�\0��/XX���\ZA�=�4����\n�Z�\'�j�D�Vp��ʷhT�Y��9�-$�o�߫!}�0\'c\\��livb:bY��9�����L��$�X��� �++\r�jy�D���\0\0\0\0X.509\0\0�0��0��O\'�\Z0\r	*�H��\r\00��1%0#	*�H��\r	Default@wsssoapbox.org10	UTH10U\nDefault ST10U	Default L10U\n	Default O10U\nDefault OU10U\nDefault CN0\r120131071122Z\r130130071122Z0��1%0#	*�H��\r	Default@wsssoapbox.org10	UTH10U\nDefault ST10U	Default L10U\n	Default O10U\nDefault OU10U\nDefault CN0�\"0\r	*�H��\r\0�\00�\n�\0�-K�\Zo�CDK�����N�t5 ��j�at��{�\\c�߰&�_�o�כL0`8,n�׾��|�{����$G��㾄Y��Z����M��dU�i++�[�i�&�7J��������\\1����38�����;����*����y,�Z{��(2o�%��f��^�BL5��k�J�����5���;��T=uY���5����㣼ҏ�i�_���{�vA2Q���PJoE���#�֨� h��rx8��������na���\00\r	*�H��\r\0�\0���v�J��(�1o\'�ۣΏgk�o6�c������g�)Mb�xC��H�Cɖ�Ӎ	A��P)�R�.� �׏��q����l�є\r:u�ZP��M@L���IO\n���_^˝O����ح���\n���>�@��N ^Fp��	<�c/(\'�\reo$����TN��� ����g+�Il؜\Z���ӝ�Vfp�8]��LUG.C^&wb�=��;I(B�����fڍ�B9L������S�-參��T�(�{���̯��)����.ٳ~�a�-','JKS','SUN','2012-02-04 00:01:56',4),(68,'server.jks','server_keystore','123456','����\0\0\0\0\0\0\0\0\0\0client_cert\0\04f��>\0X.509\0\0�0�}0��N�:�0\r	*�H��\r\00��10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0\r111222141237Z\r120321141237Z0��10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0��0\r	*�H��\r\0��\00����\0�_�\n��y<}b�\\:蘮Dˏ���}\r؈�M7Z3��սSE�����KD�ak�T����H�w�>=f=H�۳�,��.�q�OV\n�U�e�J��U4U~���9�ude7�l�ᄅ}\ZS�y99i\00\r	*�H��\r\0��\0kW\Z�d�G1��I3�$���1aRps�ʔ�c��_��qɦ]�vu��W�^��(D��B�~u��E<�&��S����a�k��\n6�G��go8dϽB�v�@���(�\\��~I7�qA�#�p�$�\0\0\0\0server_keystore\0\04f[\0\0�0��0\n+*\0��>��笽�-�Z�z�T���m��_�J��Ǌ�먮�U�tp��~Ǎ�r�h�fF_y?o� ������\"�����S�W�(�Ǩ�Z]w\"\0�_�^�-o�%�,�M�ʤs�\\��f��e6Z-i����V��� ��)\0c�@���\ny��V7�7X�M�{�ב��GQ:P.��6�\0�X\n��W��x<��t/�F��T�4;x8���9�n�X��Wk�v�`�n\0�	S��Y�b��7\"�ɡ�Ozd�\"kgƎg|�2����}J�_B�Y�u�C�\r|�UU�����4��NrީՌ�-\"���I�����>:v(=��J�b��\"Q�{G���q�GaH���4�< y�T+�����R��?R�9`9�Ȟ���f�5(��PVĕ�f\nD���*���M@�ٝJ_u9g�\r/�G+S���\"21������f%�\n��1�`ӈ;l~b�Gi�_��+��r���j��	�D\Z8&hj$�[Ok����?�`�.��x���S�H�^2F��c\r�Ŋ��̉F�a�]^�{2���NPz[�ˏ0��b��l������\ZeĿ��b�p̺f�&!�O��T1��������Q(9#%/�D�;Բ�u�+�������^\Z\r���	��QwN�l�\0���j�]i;)�->h�_C#:,��(��\'3E��F\0\0\0\0X.509\0\0k0�g0�РN�:&0\r	*�H��\r\00x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0\r111222140942Z\r120321140942Z0x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0��0\r	*�H��\r\0��\00����\0�~��!�,҃��\Z!\r�E�js۶\\#��}�\Z�ɝ\r��]�\nڹh����唗 h�T�N�\0�1@Bޖ�z��L u���Gr\n2�e�����)�t� kV�u��5��-Z$�J{ɼ�9f����/\00\r	*�H��\r\0��\02T����r�}R2&�����\"��CI�\Z�f��\n:�Y7���5��z(��T#6m�m��4�n�kןd�?�c\r1�n�,�p�}L}�t�P�!wN�X�;kI�I����qA\\��\0����tb8R�z�u��C�FY ��','JKS','SUN','2012-02-04 08:59:00',1),(71,'client.jks','client_keystore','123456','����\0\0\0\0\0\0\0\0\0\0server_cert\0\04f�e�\0X.509\0\0k0�g0�РN�:&0\r	*�H��\r\00x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0\r111222140942Z\r120321140942Z0x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0��0\r	*�H��\r\0��\00����\0�~��!�,҃��\Z!\r�E�js۶\\#��}�\Z�ɝ\r��]�\nڹh����唗 h�T�N�\0�1@Bޖ�z��L u���Gr\n2�e�����)�t� kV�u��5��-Z$�J{ɼ�9f����/\00\r	*�H��\r\0��\02T����r�}R2&�����\"��CI�\Z�f��\n:�Y7���5��z(��T#6m�m��4�n�kןd�?�c\r1�n�,�p�}L}�t�P�!wN�X�;kI�I����qA\\��\0��\0\0\0\0client cert\0\05@��\0X.509\0\0�0�}0��N�:�0\r	*�H��\r\00��10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0\r111222141237Z\r120321141237Z0��10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0��0\r	*�H��\r\0��\00����\0�_�\n��y<}b�\\:蘮Dˏ���}\r؈�M7Z3��սSE�����KD�ak�T����H�w�>=f=H�۳�,��.�q�OV\n�U�e�J��U4U~���9�ude7�l�ᄅ}\ZS�y99i\00\r	*�H��\r\0��\0kW\Z�d�G1��I3�$���1aRps�ʔ�c��_��qɦ]�vu��W�^��(D��B�~u��E<�&��S����a�k��\n6�G��go8dϽB�v�@���(�\\��~I7�qA�#�p�$�\0\0\0\0client_keystore\0\04f�u\0\0�0��0\n+*\0��<��$&���j���խ�NT��ye�K�*i���W\\��i>�$���/M?+y���cue���n�)�g�(�V[�J\\����U����<��2y.����\\���l�}��$O��w���#��e����y<,�%����;�3��#�,��8�hZ���:���[Х �9W�_��>�lŧ�&�ޯ`Y$<U��3��y>�=t\0��2yw�����S4��t������2l���a<���[%�L;˪l�>=�ݠ�mAG4)+���V���E{#bl�$�����Z,�C�<�T\05��{w���w�HWm�]SVR�h��X^����y�P�m-vqt�!����Y�Y��~*�e�<7��v��4_�2ٿ��VG`$Ko���e�;	���[��y�tG�x���2�����d�!�L�7���IT�����H��I:}N�NkF��3��~dU{�1F!jK4xY\r�l�IJO�]�Of\0ZXӏ��\n�࣓��s�	�&p�wL�/�4ӭ��{��|���r�)�R���Rkx��@ϟR������ҏ	���m<��mB.	=ԝ�E�8lQ]�J������S,f6m)�1�}�zi�(�40�%���\"�l��w�5��#�-:��}�r���*�*��BNd�H�u�趐�w.�K�\'��|pf>��n�~%���g�֒\0\0\0\0X.509\0\0�0�}0��N�:�0\r	*�H��\r\00��10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0\r111222141237Z\r120321141237Z0��10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0��0\r	*�H��\r\0��\00����\0�_�\n��y<}b�\\:蘮Dˏ���}\r؈�M7Z3��սSE�����KD�ak�T����H�w�>=f=H�۳�,��.�q�OV\n�U�e�J��U4U~���9�ude7�l�ᄅ}\ZS�y99i\00\r	*�H��\r\0��\0kW\Z�d�G1��I3�$���1aRps�ʔ�c��_��qɦ]�vu��W�^��(D��B�~u��E<�&��S����a�k��\n6�G��go8dϽB�v�@���(�\\��~I7�qA�#�p�$���Q�\r\"У��z�[3R8D','JKS','SUN','2012-02-07 08:13:53',1),(72,'too.jks',NULL,'123456','����\0\0\0\0\0\0\0�-��8�ܪ�|�8j_�-*','JKS','SUN','2012-03-21 11:40:49',2),(73,'client.jks','client_keystore','123456','����\0\0\0\0\0\0\0\0\0\0server_cert\0\04f�e�\0X.509\0\0k0�g0�РN�:&0\r	*�H��\r\00x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0\r111222140942Z\r120321140942Z0x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0��0\r	*�H��\r\0��\00����\0�~��!�,҃��\Z!\r�E�js۶\\#��}�\Z�ɝ\r��]�\nڹh����唗 h�T�N�\0�1@Bޖ�z��L u���Gr\n2�e�����)�t� kV�u��5��-Z$�J{ɼ�9f����/\00\r	*�H��\r\0��\02T����r�}R2&�����\"��CI�\Z�f��\n:�Y7���5��z(��T#6m�m��4�n�kןd�?�c\r1�n�,�p�}L}�t�P�!wN�X�;kI�I����qA\\��\0��\0\0\0\0client cert\0\05@��\0X.509\0\0�0�}0��N�:�0\r	*�H��\r\00��10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0\r111222141237Z\r120321141237Z0��10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0��0\r	*�H��\r\0��\00����\0�_�\n��y<}b�\\:蘮Dˏ���}\r؈�M7Z3��սSE�����KD�ak�T����H�w�>=f=H�۳�,��.�q�OV\n�U�e�J��U4U~���9�ude7�l�ᄅ}\ZS�y99i\00\r	*�H��\r\0��\0kW\Z�d�G1��I3�$���1aRps�ʔ�c��_��qɦ]�vu��W�^��(D��B�~u��E<�&��S����a�k��\n6�G��go8dϽB�v�@���(�\\��~I7�qA�#�p�$�\0\0\0\0client_keystore\0\04f�u\0\0�0��0\n+*\0��<��$&���j���խ�NT��ye�K�*i���W\\��i>�$���/M?+y���cue���n�)�g�(�V[�J\\����U����<��2y.����\\���l�}��$O��w���#��e����y<,�%����;�3��#�,��8�hZ���:���[Х �9W�_��>�lŧ�&�ޯ`Y$<U��3��y>�=t\0��2yw�����S4��t������2l���a<���[%�L;˪l�>=�ݠ�mAG4)+���V���E{#bl�$�����Z,�C�<�T\05��{w���w�HWm�]SVR�h��X^����y�P�m-vqt�!����Y�Y��~*�e�<7��v��4_�2ٿ��VG`$Ko���e�;	���[��y�tG�x���2�����d�!�L�7���IT�����H��I:}N�NkF��3��~dU{�1F!jK4xY\r�l�IJO�]�Of\0ZXӏ��\n�࣓��s�	�&p�wL�/�4ӭ��{��|���r�)�R���Rkx��@ϟR������ҏ	���m<��mB.	=ԝ�E�8lQ]�J������S,f6m)�1�}�zi�(�40�%���\"�l��w�5��#�-:��}�r���*�*��BNd�H�u�趐�w.�K�\'��|pf>��n�~%���g�֒\0\0\0\0X.509\0\0�0�}0��N�:�0\r	*�H��\r\00��10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0\r111222141237Z\r120321141237Z0��10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0��0\r	*�H��\r\0��\00����\0�_�\n��y<}b�\\:蘮Dˏ���}\r؈�M7Z3��սSE�����KD�ak�T����H�w�>=f=H�۳�,��.�q�OV\n�U�e�J��U4U~���9�ude7�l�ᄅ}\ZS�y99i\00\r	*�H��\r\0��\0kW\Z�d�G1��I3�$���1aRps�ʔ�c��_��qɦ]�vu��W�^��(D��B�~u��E<�&��S����a�k��\n6�G��go8dϽB�v�@���(�\\��~I7�qA�#�p�$���Q�\r\"У��z�[3R8D','JKS','SUN','2012-03-21 11:41:07',2);
/*!40000 ALTER TABLE `key_store` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `role_id` int(5) unsigned NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) NOT NULL,
  `role_desc` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'user','Regular users'),(2,'admin','Administration users'),(3,'manager-script','manager-script'),(4,'test','test'),(5,'test1','test1'),(6,'test1','test1');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_log`
--

DROP TABLE IF EXISTS `user_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_log` (
  `LOG_ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LOGGER` varchar(300) NOT NULL,
  `LEVEL` varchar(10) NOT NULL,
  `MESSAGE` varchar(3000) NOT NULL,
  `USER_ID` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`LOG_ID`),
  UNIQUE KEY `LOG_ID_UNIQUE` (`LOG_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1854 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_log`
--

LOCK TABLES `user_log` WRITE;
/*!40000 ALTER TABLE `user_log` DISABLE KEYS */;
INSERT INTO `user_log` VALUES (1851,'2012-02-08 11:38:19','WSDLImportForm:(199)constructTreeNode','INFO','Operaiton Count: 4',1),(1852,'2012-02-08 11:38:19','WSDLImportForm:(201)constructTreeNode','INFO','Operation Name : CurrentOilPrice',1),(1853,'2012-02-08 11:38:19','WSDLImportForm:(201)constructTreeNode','INFO','Operation Name : GetOilPrice',1);
/*!40000 ALTER TABLE `user_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `USER_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'peter_conair','1234','Peter','Conair'),(2,'admin','1234','Pongsak','Gransamran'),(3,'peter','1234','Peter','Peter lastname'),(4,'tomcat','1234','Tomcat user','Tomcat ...');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_roles` (
  `user_id` int(10) NOT NULL,
  `role_id` int(10) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `fk_users_has_roles_roles1` (`role_id`),
  KEY `fk_users_has_roles_users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` VALUES (1,1),(3,1),(2,2),(4,3);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `v_user_role`
--

DROP TABLE IF EXISTS `v_user_role`;
/*!50001 DROP VIEW IF EXISTS `v_user_role`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `v_user_role` (
  `username` varchar(50),
  `password` varchar(50),
  `role_name` varchar(20)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Current Database: `wsssoapboxdb`
--

USE `wsssoapboxdb`;

--
-- Final view structure for view `v_user_role`
--

/*!50001 DROP TABLE IF EXISTS `v_user_role`*/;
/*!50001 DROP VIEW IF EXISTS `v_user_role`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_user_role` AS select `u`.`username` AS `username`,`u`.`password` AS `password`,`r`.`role_name` AS `role_name` from ((`users_roles` `ur` join `users` `u` on((`u`.`USER_ID` = `ur`.`user_id`))) join `roles` `r` on((`r`.`role_id` = `ur`.`role_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-03-30 22:37:47
