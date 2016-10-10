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
INSERT INTO `key_store` VALUES (60,'client.jks','client_keystore','123456','şíşí\0\0\0\0\0\0\0\0\0\0server_cert\0\04f“eÒ\0X.509\0\0k0‚g0‚Ğ Nó:&0\r	*†H†÷\r\00x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0\r111222140942Z\r120321140942Z0x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0Ÿ0\r	*†H†÷\r\0\00‰\0‡~Ûñ!Ÿ,Òƒœé\Z!\rùEÊjsÛ¶\\#š¬}¯\Zî¶É\r«İ]Ì\nÚ¹hÆåàîå”— h¡TéN¹\0î1@BŞ–Ézõ„L u°—›Gr\n2øeËĞÜäø)ãtƒ kVüuöİ5°‘-Z$ÇJ{É¼ß9fç…—ª/\00\r	*†H†÷\r\0\02T¢¶ñ÷rÔ}R2&“½Üüã\"­ğ¼ºCI·\ZşfÚÁ\n:ÌY7ä™ü5çÑz(ç­ğT#6mÙmöë4·n¼k×Ÿdˆ?çc\r1Îné,“p¬}L}‚tÒPÓ!wNŞXœ;kI¶I €’ÛqA\\”ê\0üØ\0\0\0\0client cert\0\05@úú\0X.509\0\00‚}0‚æ Nó:Õ0\r	*†H†÷\r\00‚10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0\r111222141237Z\r120321141237Z0‚10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0Ÿ0\r	*†H†÷\r\0\00‰\0¦_¦\n–èy<}bÈ\\:è˜®DËŸšçš}\rØˆëM7Z3”åÕ½SEÖÄúêîKDak¬T¸éğÑHıwı>=f=HÛ³ğ,¥¨.à¹q€OV\nUñ’eÏJ‚ÃU4U~¯¶ÿ9™ude7ılªá„…}\ZSûy99i\00\r	*†H†÷\r\0\0kW\Z—d¾G1ıñI3½$ÜÈ1aRps½Ê”¯cıš_úÂqÉ¦]vuµòWÑ^½½(D¹öBÄ~uçÒE<…& ¢SŠ‡Íña—kë—\n6¬Gººgo8dÏ½BÓvú@´§õ(ã«\\Ùó~I7ıqA #–pì$ğ\0\0\0\0client_keystore\0\04fèu\0\0º0‚¶0\n+*\0‚¢<´â$&  »jŸ§ŒÕ­’NT‹yeK¤*iğäÃW\\ÔÆi>¯$‚ëÚ/M?+yÁ­cue™´³nÿ)è…gÅ(åV[¡J\\îãô½Uœ™ìµ‹<ÛÄ2y.…Éø©\\’‰Èló}º¨$O³÷wøËş#òÆeü¤y<,¯%òæÏÑ;×3ÿõ#˜,—8ñhZÿÒõ:›‰ß[Ğ¥ š9W‰_´¦>æŠlÅ§Ÿ&öŞ¯`Y$<Uë­é3ñİy>ê=t\0äì2yw÷ùßªéS4¢¢t¦À¼È2lº•îa<øŸ»[%ÅL;ËªlÙ>=ğİ ¨mAG4)+òõ›V§ÿE{#bl”$Š˜Ú×ÌZ,ÊC£<°T\05ÿ{wñÍåw—HWm°]SVR°hˆõX^¸„¢´yÙPşm-vqtö!ÃçÎÓY£YÀ‡~*ëeä<7‘vÀä4_¼2Ù¿²˜VG`$Koú½üe;	Ò÷®[ÄÙy tG“xİò2»±¦ùäd„!î£L7¦°¸ITı íà”Hµ¿I:}NÁNkFÇ‰3ë~dU{•1F!jK4xY\rƒlºIJOÏ]ÑOf\0ZXÓˆÁ\n¼à£“«ŠsÕ	’&p˜wLà/ó4Ó­àî{ª»|ÄÏìr¹)İRİÁÿRkxò@ÏŸR¤ƒèäãŞÒ	ê÷¤m<¥ûmB.	=Ô†Eº8lQ]»Jãáçîú¬S,f6m)©1ğ¹}ÌziÔ(Ì40Î%ù¸²\"µlŒ±w³5€¢#Ã-:º¾}rÄïİ*İ*ŸóBNd±HÃuÛè¶€w.³KÍ\'ø•|pf>øæn‡~%ç©‰ÎgšÖ’\0\0\0\0X.509\0\00‚}0‚æ Nó:Õ0\r	*†H†÷\r\00‚10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0\r111222141237Z\r120321141237Z0‚10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0Ÿ0\r	*†H†÷\r\0\00‰\0¦_¦\n–èy<}bÈ\\:è˜®DËŸšçš}\rØˆëM7Z3”åÕ½SEÖÄúêîKDak¬T¸éğÑHıwı>=f=HÛ³ğ,¥¨.à¹q€OV\nUñ’eÏJ‚ÃU4U~¯¶ÿ9™ude7ılªá„…}\ZSûy99i\00\r	*†H†÷\r\0\0kW\Z—d¾G1ıñI3½$ÜÈ1aRps½Ê”¯cıš_úÂqÉ¦]vuµòWÑ^½½(D¹öBÄ~uçÒE<…& ¢SŠ‡Íña—kë—\n6¬Gººgo8dÏ½BÓvú@´§õ(ã«\\Ùó~I7ıqA #–pì$ğà¯é´Q³\r\"Ğ£æÌz¿[3R8D','JKS','SUN','2012-02-03 06:25:39',2),(61,'server.jks','server_keystore','123456','şíşí\0\0\0\0\0\0\0\0\0\0client_cert\0\04f’>\0X.509\0\00‚}0‚æ Nó:Õ0\r	*†H†÷\r\00‚10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0\r111222141237Z\r120321141237Z0‚10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0Ÿ0\r	*†H†÷\r\0\00‰\0¦_¦\n–èy<}bÈ\\:è˜®DËŸšçš}\rØˆëM7Z3”åÕ½SEÖÄúêîKDak¬T¸éğÑHıwı>=f=HÛ³ğ,¥¨.à¹q€OV\nUñ’eÏJ‚ÃU4U~¯¶ÿ9™ude7ılªá„…}\ZSûy99i\00\r	*†H†÷\r\0\0kW\Z—d¾G1ıñI3½$ÜÈ1aRps½Ê”¯cıš_úÂqÉ¦]vuµòWÑ^½½(D¹öBÄ~uçÒE<…& ¢SŠ‡Íña—kë—\n6¬Gººgo8dÏ½BÓvú@´§õ(ã«\\Ùó~I7ıqA #–pì$ğ\0\0\0\0server_keystore\0\04f[\0\0¹0‚µ0\n+*\0‚¡>„Ñç¬½“-ÈZízãTç«å¡Ímöó_éJàˆÇŠÆë¨®ŸUî—tp¼Ş~Çÿr…hÔfF_y?o® ãªÛÃåİã\"úåîĞÓSÌW×(ÌÇ¨„Z]w\"\0ı_Û^È-o‡%¾,®MëÊ¤s \\”³fíøe6Z-i‘ÎÙÓVöÓï ¡›)\0cé@•¡ı\ny— V7ì7X Mö{ğ“×‘˜ÔGQ:P.ÿÌ6ê\0³X\nç‰ÆW‹¸x<ËÜt/ÚF¡T€4;x8ş¨ë9ünÊXÿ«Wk™v„`İn\0Ê	SóÙYÛbÓ7\"¹É¡İOzd¸\"kgÆg|€2¹•àš}Jƒ_BùYÉuCß\r|İUU€ûËççš4íÓNrŞ©ÕŒ„-\"ÔÒéIê÷ãåË>:v(=¥šJÚbğì\"Qƒ{G†—²qƒGaHÉËò¸4Š< yşT+ÒóßıR‡Ü?Rˆ9`9ÃÈˆõûfè5(……PVÄ•Äf\nD“í*ŠØğ£M@×ÙJ_u9gã\r/ãG+S–á\"21¯óĞÿf%‘\n¾ˆ1·`Óˆ;l~b¼Giâ_‡‘+ßÌr›Ğj´	´D\Z8&hj$ë[Ok‡­Ê?‚`«.»àxÆØûS“HÔ^2FÊc\rşÅŠåì›Ì‰FÂaË]^Î{2ÆåµØNPz[ıË0ş™b‡lÏüˆüÉ\ZeÄ¿Ğçb¹pÌºf¼&!ÇO¾ºT1ëŞïé¼åÑÀQ(9#%/ªD‚;Ô²Œuš+¢ğœ“ŸĞÉ^\Z\r¸æŒ	¸†QwNíl´\0·ÃÜj…]i;)ç->hÒ_C#:,ÔÏ(šú\'3E›èF\0\0\0\0X.509\0\0k0‚g0‚Ğ Nó:&0\r	*†H†÷\r\00x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0\r111222140942Z\r120321140942Z0x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0Ÿ0\r	*†H†÷\r\0\00‰\0‡~Ûñ!Ÿ,Òƒœé\Z!\rùEÊjsÛ¶\\#š¬}¯\Zî¶É\r«İ]Ì\nÚ¹hÆåàîå”— h¡TéN¹\0î1@BŞ–Ézõ„L u°—›Gr\n2øeËĞÜäø)ãtƒ kVüuöİ5°‘-Z$ÇJ{É¼ß9fç…—ª/\00\r	*†H†÷\r\0\02T¢¶ñ÷rÔ}R2&“½Üüã\"­ğ¼ºCI·\ZşfÚÁ\n:ÌY7ä™ü5çÑz(ç­ğT#6mÙmöë4·n¼k×Ÿdˆ?çc\r1Îné,“p¬}L}‚tÒPÓ!wNŞXœ;kI¶I €’ÛqA\\”ê\0üØ‰’tb8R©z÷uŒC’FY Ûê','JKS','SUN','2012-02-03 22:54:59',2),(63,'server.jks','server_keystore','123456','şíşí\0\0\0\0\0\0\0\0\0\0client_cert\0\04f’>\0X.509\0\00‚}0‚æ Nó:Õ0\r	*†H†÷\r\00‚10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0\r111222141237Z\r120321141237Z0‚10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0Ÿ0\r	*†H†÷\r\0\00‰\0¦_¦\n–èy<}bÈ\\:è˜®DËŸšçš}\rØˆëM7Z3”åÕ½SEÖÄúêîKDak¬T¸éğÑHıwı>=f=HÛ³ğ,¥¨.à¹q€OV\nUñ’eÏJ‚ÃU4U~¯¶ÿ9™ude7ılªá„…}\ZSûy99i\00\r	*†H†÷\r\0\0kW\Z—d¾G1ıñI3½$ÜÈ1aRps½Ê”¯cıš_úÂqÉ¦]vuµòWÑ^½½(D¹öBÄ~uçÒE<…& ¢SŠ‡Íña—kë—\n6¬Gººgo8dÏ½BÓvú@´§õ(ã«\\Ùó~I7ıqA #–pì$ğ\0\0\0\0server_keystore\0\04f[\0\0¹0‚µ0\n+*\0‚¡>„Ñç¬½“-ÈZízãTç«å¡Ímöó_éJàˆÇŠÆë¨®ŸUî—tp¼Ş~Çÿr…hÔfF_y?o® ãªÛÃåİã\"úåîĞÓSÌW×(ÌÇ¨„Z]w\"\0ı_Û^È-o‡%¾,®MëÊ¤s \\”³fíøe6Z-i‘ÎÙÓVöÓï ¡›)\0cé@•¡ı\ny— V7ì7X Mö{ğ“×‘˜ÔGQ:P.ÿÌ6ê\0³X\nç‰ÆW‹¸x<ËÜt/ÚF¡T€4;x8ş¨ë9ünÊXÿ«Wk™v„`İn\0Ê	SóÙYÛbÓ7\"¹É¡İOzd¸\"kgÆg|€2¹•àš}Jƒ_BùYÉuCß\r|İUU€ûËççš4íÓNrŞ©ÕŒ„-\"ÔÒéIê÷ãåË>:v(=¥šJÚbğì\"Qƒ{G†—²qƒGaHÉËò¸4Š< yşT+ÒóßıR‡Ü?Rˆ9`9ÃÈˆõûfè5(……PVÄ•Äf\nD“í*ŠØğ£M@×ÙJ_u9gã\r/ãG+S–á\"21¯óĞÿf%‘\n¾ˆ1·`Óˆ;l~b¼Giâ_‡‘+ßÌr›Ğj´	´D\Z8&hj$ë[Ok‡­Ê?‚`«.»àxÆØûS“HÔ^2FÊc\rşÅŠåì›Ì‰FÂaË]^Î{2ÆåµØNPz[ıË0ş™b‡lÏüˆüÉ\ZeÄ¿Ğçb¹pÌºf¼&!ÇO¾ºT1ëŞïé¼åÑÀQ(9#%/ªD‚;Ô²Œuš+¢ğœ“ŸĞÉ^\Z\r¸æŒ	¸†QwNíl´\0·ÃÜj…]i;)ç->hÒ_C#:,ÔÏ(šú\'3E›èF\0\0\0\0X.509\0\0k0‚g0‚Ğ Nó:&0\r	*†H†÷\r\00x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0\r111222140942Z\r120321140942Z0x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0Ÿ0\r	*†H†÷\r\0\00‰\0‡~Ûñ!Ÿ,Òƒœé\Z!\rùEÊjsÛ¶\\#š¬}¯\Zî¶É\r«İ]Ì\nÚ¹hÆåàîå”— h¡TéN¹\0î1@BŞ–Ézõ„L u°—›Gr\n2øeËĞÜäø)ãtƒ kVüuöİ5°‘-Z$ÇJ{É¼ß9fç…—ª/\00\r	*†H†÷\r\0\02T¢¶ñ÷rÔ}R2&“½Üüã\"­ğ¼ºCI·\ZşfÚÁ\n:ÌY7ä™ü5çÑz(ç­ğT#6mÙmöë4·n¼k×Ÿdˆ?çc\r1Îné,“p¬}L}‚tÒPÓ!wNŞXœ;kI¶I €’ÛqA\\”ê\0üØ‰’tb8R©z÷uŒC’FY Ûê','JKS','SUN','2012-02-03 23:56:29',4),(64,'default.jks','default_keytore','123456','şíşí\0\0\0\0\0\0\0\0\0\0client_cert\0\052œÌÍ\0X.509\0\00‚}0‚æ Nó:Õ0\r	*†H†÷\r\00‚10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0\r111222141237Z\r120321141237Z0‚10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0Ÿ0\r	*†H†÷\r\0\00‰\0¦_¦\n–èy<}bÈ\\:è˜®DËŸšçš}\rØˆëM7Z3”åÕ½SEÖÄúêîKDak¬T¸éğÑHıwı>=f=HÛ³ğ,¥¨.à¹q€OV\nUñ’eÏJ‚ÃU4U~¯¶ÿ9™ude7ılªá„…}\ZSûy99i\00\r	*†H†÷\r\0\0kW\Z—d¾G1ıñI3½$ÜÈ1aRps½Ê”¯cıš_úÂqÉ¦]vuµòWÑ^½½(D¹öBÄ~uçÒE<…& ¢SŠ‡Íña—kë—\n6¬Gººgo8dÏ½BÓvú@´§õ(ã«\\Ùó~I7ıqA #–pì$ğ\0\0\0\0server_cert\0\052œ“È\0X.509\0\0k0‚g0‚Ğ Nó:&0\r	*†H†÷\r\00x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0\r111222140942Z\r120321140942Z0x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0Ÿ0\r	*†H†÷\r\0\00‰\0‡~Ûñ!Ÿ,Òƒœé\Z!\rùEÊjsÛ¶\\#š¬}¯\Zî¶É\r«İ]Ì\nÚ¹hÆåàîå”— h¡TéN¹\0î1@BŞ–Ézõ„L u°—›Gr\n2øeËĞÜäø)ãtƒ kVüuöİ5°‘-Z$ÇJ{É¼ß9fç…—ª/\00\r	*†H†÷\r\0\02T¢¶ñ÷rÔ}R2&“½Üüã\"­ğ¼ºCI·\ZşfÚÁ\n:ÌY7ä™ü5çÑz(ç­ğT#6mÙmöë4·n¼k×Ÿdˆ?çc\r1Îné,“p¬}L}‚tÒPÓ!wNŞXœ;kI¶I €’ÛqA\\”ê\0üØ\0\0\0\0default_keytore\0\052›#\0\00‚ş0\n+*\0‚ê9è81¬¹`Ş~ä#PÀe_¼B`<åÉñ·ˆÕ-U™ni=Çô¬ÉÉ¿²6í2xYê6šWÇø¯¯HZ*[˜›wrb\n/Éàaœ|èyb Zl¹ŠcJ*4AùÅ\n¨úNÎZ%J)m	W?)†ƒÙL AKJûm¢h¨Q©…·îs1tÌõä«Öì\'¨à>õáèÚCºğQØZlÛŒÎ0,Û1á\"RŠ9…{æHtò\0²IFBÑË!;íÿı¦“q.EîjÎº<Óú/mñ©¬wSFC1.ü¼|sÉì/&<UGõŞHHA#•ÌâŞiìµİ<£†ß JYáŞÜ”)Ÿ1nßC‰´…½ı²=W\'>ıü‹³´aodÚUÜ‰ğ?\rÄİ\0¤´•jà&ö3_²;ÓJ:ÓªbyeªydAK!/ğÕ¾±l0MöÊ¹†5$ìz>Ï³ú»•¥3p¤ÖÂ:?£Šç%K«‘¢=ÀMÙmŠnr[ôÜĞUï~àF)”{ º±x$‘).®¸¤n¡Yº†ÎâŒ²†R¬¹Y”ÚËZÒÊ4¯TïÔ\rtô¼ò,£;ÂÂ¬\"\"ËèëãĞf€\n»‘´J¿	rªëÙöœvçbÃ%zVä$¾W÷å€|½-Ø—*’ßào–àI1bæD‘C“B¾U%kqãL>óTŞï\nÚyÍÿ•¡8|N,ÇôıÕ‘¸kF {OtfKZèr´[\rÔUØX-É?j“ñ;4à¾ÅD·7No$d¸«çTn<tÜàLfµ-™QøÄÉ^©€@°G.l‰Ê@ĞØæ`UÌ1@K×bû—ù¸E&m\n$EˆÉJ{†°.õs5{<S¼~Ùœ~dòƒ>¥Šm°½&i}h‘Èzxs‰@øÏêÅu&ò•I¶LºR³ãÑıóGCÃB‹nÃSÜ6Kœ©€LXë\nrüì\"E‡\\öÎ°-ñk¹¾F…}‘Rª½ÊÄH¯áşÛO8½…@3±†…JÙH½«6p\Zéä…\nB’SÄÓ6\"µº¨¯~x&ÈÏELR8ˆº4h6™{…çN3[íˆİº\Z5y¨\\½*÷F™É<å©šî»ú¸35gâ°e\"¶ôJæ7«Ã\0YsökçsğÅïZsN®ìn™ºDÁ\\E³1ß¾0ÕÉ\nÄà¶áãY(³j4“6×—¼Ö9Ç[nJ¢rÅÃ~_|½~Ôô¥ºüg‹ÑQ|¹®.Iç(7k#Öæ¼m	g8d Ñ¢F‰…µµ4€íés\\³V\nä¢İ¦¢Bä6Ào#J4¹Áh’9ûC]ä_2¶@«Ó!íL×¡ˆ-%É Î\\Öòo‚ú=¼7üùH#‹È)G/ˆŒa†ËXÎX:ü(7‡öôÎ8\'Ş5ƒ‹ÅqK[Ï¼¢p†2{a«;–r´ˆ9¸›ï•q+šº÷ÆMÖ\n-ôj+ä\0›†/XX÷”\ZAá=¸4úº·ã‡\nÊZ\'ç¨jïD–Vp£ÜÊ·hT‡Y“Ô9Ò-$‰oÿß«!}¬0\'c\\Øğlivb:bY·Ø9³ÿƒÆÍL¦Ÿ$¿Xùù– ½++\rğjyÊD‚˜ù\0\0\0\0X.509\0\0³0‚¯0‚—O\'”\Z0\r	*†H†÷\r\00›1%0#	*†H†÷\r	Default@wsssoapbox.org10	UTH10U\nDefault ST10U	Default L10U\n	Default O10U\nDefault OU10U\nDefault CN0\r120131071122Z\r130130071122Z0›1%0#	*†H†÷\r	Default@wsssoapbox.org10	UTH10U\nDefault ST10U	Default L10U\n	Default O10U\nDefault OU10U\nDefault CN0‚\"0\r	*†H†÷\r\0‚\00‚\n‚\0Å-Kâ¼\ZoÜCDK¶õÄÂÅNÆt5 è¾êj‘atå€{Í\\c×ß°&º_ o¼×›L0`8,n×¾„‡|¹{ÉÜ÷¸$G‡Ïã¾„Yù¥Z‡ÃÓÉMõ°dU½i++Ğ[ûi&à7JãÁ…Ÿä×ßŞ\\1¥…¯Í38®«¦êÙ;Áÿ‰Á*öãy,‹Z{¾Ö(2oƒ%´¶fœÌ^ÙBL5ÓkÓJ‰ı¦­5ÓÖş;öéT=uYØÓá5ÆæÒîºã£¼Òåi_–Çä{ÎvA2Q€ÅPJoEĞ¤Ô#§Ö¨Ú hàšrx8Á§·ÓşŸñna¹Æã\00\r	*†H†÷\r\0‚\0ÆÏğ¸vŠJ£ö(1o\'‹Û£ÎgkÊo6÷c¹ÿÛú“”gÿ)MbxCÖÓHî™CÉ–©Ó	A‡šP)ßRÑ.¯ ‚×³qı–²Îlï°Ñ”\r:uûZPºîM@Lô¦şIO\nÆÖØ_^ËOÅËŞıØ­û“ğ\n×Üñ>™@œ„N ^Fp‡Ú	<ïc/(\'İ\reo$‚—é¤èTNâ…ìç ½ş§€g+†IlØœ\Z¸¹Ó±Vfpè8]ÎşLUG.C^&wbì=ü¨;I(Bû—³ÜfÚ€B9L¦ùÄÛİóS¬-ï¥«À™TÜ({Š¹æÌ¯½î)‚².Ù³~âaò-','JKS','SUN','2012-02-04 00:01:56',4),(68,'server.jks','server_keystore','123456','şíşí\0\0\0\0\0\0\0\0\0\0client_cert\0\04f’>\0X.509\0\00‚}0‚æ Nó:Õ0\r	*†H†÷\r\00‚10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0\r111222141237Z\r120321141237Z0‚10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0Ÿ0\r	*†H†÷\r\0\00‰\0¦_¦\n–èy<}bÈ\\:è˜®DËŸšçš}\rØˆëM7Z3”åÕ½SEÖÄúêîKDak¬T¸éğÑHıwı>=f=HÛ³ğ,¥¨.à¹q€OV\nUñ’eÏJ‚ÃU4U~¯¶ÿ9™ude7ılªá„…}\ZSûy99i\00\r	*†H†÷\r\0\0kW\Z—d¾G1ıñI3½$ÜÈ1aRps½Ê”¯cıš_úÂqÉ¦]vuµòWÑ^½½(D¹öBÄ~uçÒE<…& ¢SŠ‡Íña—kë—\n6¬Gººgo8dÏ½BÓvú@´§õ(ã«\\Ùó~I7ıqA #–pì$ğ\0\0\0\0server_keystore\0\04f[\0\0¹0‚µ0\n+*\0‚¡>„Ñç¬½“-ÈZízãTç«å¡Ímöó_éJàˆÇŠÆë¨®ŸUî—tp¼Ş~Çÿr…hÔfF_y?o® ãªÛÃåİã\"úåîĞÓSÌW×(ÌÇ¨„Z]w\"\0ı_Û^È-o‡%¾,®MëÊ¤s \\”³fíøe6Z-i‘ÎÙÓVöÓï ¡›)\0cé@•¡ı\ny— V7ì7X Mö{ğ“×‘˜ÔGQ:P.ÿÌ6ê\0³X\nç‰ÆW‹¸x<ËÜt/ÚF¡T€4;x8ş¨ë9ünÊXÿ«Wk™v„`İn\0Ê	SóÙYÛbÓ7\"¹É¡İOzd¸\"kgÆg|€2¹•àš}Jƒ_BùYÉuCß\r|İUU€ûËççš4íÓNrŞ©ÕŒ„-\"ÔÒéIê÷ãåË>:v(=¥šJÚbğì\"Qƒ{G†—²qƒGaHÉËò¸4Š< yşT+ÒóßıR‡Ü?Rˆ9`9ÃÈˆõûfè5(……PVÄ•Äf\nD“í*ŠØğ£M@×ÙJ_u9gã\r/ãG+S–á\"21¯óĞÿf%‘\n¾ˆ1·`Óˆ;l~b¼Giâ_‡‘+ßÌr›Ğj´	´D\Z8&hj$ë[Ok‡­Ê?‚`«.»àxÆØûS“HÔ^2FÊc\rşÅŠåì›Ì‰FÂaË]^Î{2ÆåµØNPz[ıË0ş™b‡lÏüˆüÉ\ZeÄ¿Ğçb¹pÌºf¼&!ÇO¾ºT1ëŞïé¼åÑÀQ(9#%/ªD‚;Ô²Œuš+¢ğœ“ŸĞÉ^\Z\r¸æŒ	¸†QwNíl´\0·ÃÜj…]i;)ç->hÒ_C#:,ÔÏ(šú\'3E›èF\0\0\0\0X.509\0\0k0‚g0‚Ğ Nó:&0\r	*†H†÷\r\00x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0\r111222140942Z\r120321140942Z0x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0Ÿ0\r	*†H†÷\r\0\00‰\0‡~Ûñ!Ÿ,Òƒœé\Z!\rùEÊjsÛ¶\\#š¬}¯\Zî¶É\r«İ]Ì\nÚ¹hÆåàîå”— h¡TéN¹\0î1@BŞ–Ézõ„L u°—›Gr\n2øeËĞÜäø)ãtƒ kVüuöİ5°‘-Z$ÇJ{É¼ß9fç…—ª/\00\r	*†H†÷\r\0\02T¢¶ñ÷rÔ}R2&“½Üüã\"­ğ¼ºCI·\ZşfÚÁ\n:ÌY7ä™ü5çÑz(ç­ğT#6mÙmöë4·n¼k×Ÿdˆ?çc\r1Îné,“p¬}L}‚tÒPÓ!wNŞXœ;kI¶I €’ÛqA\\”ê\0üØ‰’tb8R©z÷uŒC’FY Ûê','JKS','SUN','2012-02-04 08:59:00',1),(71,'client.jks','client_keystore','123456','şíşí\0\0\0\0\0\0\0\0\0\0server_cert\0\04f“eÒ\0X.509\0\0k0‚g0‚Ğ Nó:&0\r	*†H†÷\r\00x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0\r111222140942Z\r120321140942Z0x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0Ÿ0\r	*†H†÷\r\0\00‰\0‡~Ûñ!Ÿ,Òƒœé\Z!\rùEÊjsÛ¶\\#š¬}¯\Zî¶É\r«İ]Ì\nÚ¹hÆåàîå”— h¡TéN¹\0î1@BŞ–Ézõ„L u°—›Gr\n2øeËĞÜäø)ãtƒ kVüuöİ5°‘-Z$ÇJ{É¼ß9fç…—ª/\00\r	*†H†÷\r\0\02T¢¶ñ÷rÔ}R2&“½Üüã\"­ğ¼ºCI·\ZşfÚÁ\n:ÌY7ä™ü5çÑz(ç­ğT#6mÙmöë4·n¼k×Ÿdˆ?çc\r1Îné,“p¬}L}‚tÒPÓ!wNŞXœ;kI¶I €’ÛqA\\”ê\0üØ\0\0\0\0client cert\0\05@úú\0X.509\0\00‚}0‚æ Nó:Õ0\r	*†H†÷\r\00‚10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0\r111222141237Z\r120321141237Z0‚10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0Ÿ0\r	*†H†÷\r\0\00‰\0¦_¦\n–èy<}bÈ\\:è˜®DËŸšçš}\rØˆëM7Z3”åÕ½SEÖÄúêîKDak¬T¸éğÑHıwı>=f=HÛ³ğ,¥¨.à¹q€OV\nUñ’eÏJ‚ÃU4U~¯¶ÿ9™ude7ılªá„…}\ZSûy99i\00\r	*†H†÷\r\0\0kW\Z—d¾G1ıñI3½$ÜÈ1aRps½Ê”¯cıš_úÂqÉ¦]vuµòWÑ^½½(D¹öBÄ~uçÒE<…& ¢SŠ‡Íña—kë—\n6¬Gººgo8dÏ½BÓvú@´§õ(ã«\\Ùó~I7ıqA #–pì$ğ\0\0\0\0client_keystore\0\04fèu\0\0º0‚¶0\n+*\0‚¢<´â$&  »jŸ§ŒÕ­’NT‹yeK¤*iğäÃW\\ÔÆi>¯$‚ëÚ/M?+yÁ­cue™´³nÿ)è…gÅ(åV[¡J\\îãô½Uœ™ìµ‹<ÛÄ2y.…Éø©\\’‰Èló}º¨$O³÷wøËş#òÆeü¤y<,¯%òæÏÑ;×3ÿõ#˜,—8ñhZÿÒõ:›‰ß[Ğ¥ š9W‰_´¦>æŠlÅ§Ÿ&öŞ¯`Y$<Uë­é3ñİy>ê=t\0äì2yw÷ùßªéS4¢¢t¦À¼È2lº•îa<øŸ»[%ÅL;ËªlÙ>=ğİ ¨mAG4)+òõ›V§ÿE{#bl”$Š˜Ú×ÌZ,ÊC£<°T\05ÿ{wñÍåw—HWm°]SVR°hˆõX^¸„¢´yÙPşm-vqtö!ÃçÎÓY£YÀ‡~*ëeä<7‘vÀä4_¼2Ù¿²˜VG`$Koú½üe;	Ò÷®[ÄÙy tG“xİò2»±¦ùäd„!î£L7¦°¸ITı íà”Hµ¿I:}NÁNkFÇ‰3ë~dU{•1F!jK4xY\rƒlºIJOÏ]ÑOf\0ZXÓˆÁ\n¼à£“«ŠsÕ	’&p˜wLà/ó4Ó­àî{ª»|ÄÏìr¹)İRİÁÿRkxò@ÏŸR¤ƒèäãŞÒ	ê÷¤m<¥ûmB.	=Ô†Eº8lQ]»Jãáçîú¬S,f6m)©1ğ¹}ÌziÔ(Ì40Î%ù¸²\"µlŒ±w³5€¢#Ã-:º¾}rÄïİ*İ*ŸóBNd±HÃuÛè¶€w.³KÍ\'ø•|pf>øæn‡~%ç©‰ÎgšÖ’\0\0\0\0X.509\0\00‚}0‚æ Nó:Õ0\r	*†H†÷\r\00‚10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0\r111222141237Z\r120321141237Z0‚10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0Ÿ0\r	*†H†÷\r\0\00‰\0¦_¦\n–èy<}bÈ\\:è˜®DËŸšçš}\rØˆëM7Z3”åÕ½SEÖÄúêîKDak¬T¸éğÑHıwı>=f=HÛ³ğ,¥¨.à¹q€OV\nUñ’eÏJ‚ÃU4U~¯¶ÿ9™ude7ılªá„…}\ZSûy99i\00\r	*†H†÷\r\0\0kW\Z—d¾G1ıñI3½$ÜÈ1aRps½Ê”¯cıš_úÂqÉ¦]vuµòWÑ^½½(D¹öBÄ~uçÒE<…& ¢SŠ‡Íña—kë—\n6¬Gººgo8dÏ½BÓvú@´§õ(ã«\\Ùó~I7ıqA #–pì$ğà¯é´Q³\r\"Ğ£æÌz¿[3R8D','JKS','SUN','2012-02-07 08:13:53',1),(72,'too.jks',NULL,'123456','şíşí\0\0\0\0\0\0\0ç -ƒâ8ÒÜª|ş8j_î-*','JKS','SUN','2012-03-21 11:40:49',2),(73,'client.jks','client_keystore','123456','şíşí\0\0\0\0\0\0\0\0\0\0server_cert\0\04f“eÒ\0X.509\0\0k0‚g0‚Ğ Nó:&0\r	*†H†÷\r\00x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0\r111222140942Z\r120321140942Z0x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0Ÿ0\r	*†H†÷\r\0\00‰\0‡~Ûñ!Ÿ,Òƒœé\Z!\rùEÊjsÛ¶\\#š¬}¯\Zî¶É\r«İ]Ì\nÚ¹hÆåàîå”— h¡TéN¹\0î1@BŞ–Ézõ„L u°—›Gr\n2øeËĞÜäø)ãtƒ kVüuöİ5°‘-Z$ÇJ{É¼ß9fç…—ª/\00\r	*†H†÷\r\0\02T¢¶ñ÷rÔ}R2&“½Üüã\"­ğ¼ºCI·\ZşfÚÁ\n:ÌY7ä™ü5çÑz(ç­ğT#6mÙmöë4·n¼k×Ÿdˆ?çc\r1Îné,“p¬}L}‚tÒPÓ!wNŞXœ;kI¶I €’ÛqA\\”ê\0üØ\0\0\0\0client cert\0\05@úú\0X.509\0\00‚}0‚æ Nó:Õ0\r	*†H†÷\r\00‚10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0\r111222141237Z\r120321141237Z0‚10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0Ÿ0\r	*†H†÷\r\0\00‰\0¦_¦\n–èy<}bÈ\\:è˜®DËŸšçš}\rØˆëM7Z3”åÕ½SEÖÄúêîKDak¬T¸éğÑHıwı>=f=HÛ³ğ,¥¨.à¹q€OV\nUñ’eÏJ‚ÃU4U~¯¶ÿ9™ude7ılªá„…}\ZSûy99i\00\r	*†H†÷\r\0\0kW\Z—d¾G1ıñI3½$ÜÈ1aRps½Ê”¯cıš_úÂqÉ¦]vuµòWÑ^½½(D¹öBÄ~uçÒE<…& ¢SŠ‡Íña—kë—\n6¬Gººgo8dÏ½BÓvú@´§õ(ã«\\Ùó~I7ıqA #–pì$ğ\0\0\0\0client_keystore\0\04fèu\0\0º0‚¶0\n+*\0‚¢<´â$&  »jŸ§ŒÕ­’NT‹yeK¤*iğäÃW\\ÔÆi>¯$‚ëÚ/M?+yÁ­cue™´³nÿ)è…gÅ(åV[¡J\\îãô½Uœ™ìµ‹<ÛÄ2y.…Éø©\\’‰Èló}º¨$O³÷wøËş#òÆeü¤y<,¯%òæÏÑ;×3ÿõ#˜,—8ñhZÿÒõ:›‰ß[Ğ¥ š9W‰_´¦>æŠlÅ§Ÿ&öŞ¯`Y$<Uë­é3ñİy>ê=t\0äì2yw÷ùßªéS4¢¢t¦À¼È2lº•îa<øŸ»[%ÅL;ËªlÙ>=ğİ ¨mAG4)+òõ›V§ÿE{#bl”$Š˜Ú×ÌZ,ÊC£<°T\05ÿ{wñÍåw—HWm°]SVR°hˆõX^¸„¢´yÙPşm-vqtö!ÃçÎÓY£YÀ‡~*ëeä<7‘vÀä4_¼2Ù¿²˜VG`$Koú½üe;	Ò÷®[ÄÙy tG“xİò2»±¦ùäd„!î£L7¦°¸ITı íà”Hµ¿I:}NÁNkFÇ‰3ë~dU{•1F!jK4xY\rƒlºIJOÏ]ÑOf\0ZXÓˆÁ\n¼à£“«ŠsÕ	’&p˜wLà/ó4Ó­àî{ª»|ÄÏìr¹)İRİÁÿRkxò@ÏŸR¤ƒèäãŞÒ	ê÷¤m<¥ûmB.	=Ô†Eº8lQ]»Jãáçîú¬S,f6m)©1ğ¹}ÌziÔ(Ì40Î%ù¸²\"µlŒ±w³5€¢#Ã-:º¾}rÄïİ*İ*ŸóBNd±HÃuÛè¶€w.³KÍ\'ø•|pf>øæn‡~%ç©‰ÎgšÖ’\0\0\0\0X.509\0\00‚}0‚æ Nó:Õ0\r	*†H†÷\r\00‚10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0\r111222141237Z\r120321141237Z0‚10	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0Ÿ0\r	*†H†÷\r\0\00‰\0¦_¦\n–èy<}bÈ\\:è˜®DËŸšçš}\rØˆëM7Z3”åÕ½SEÖÄúêîKDak¬T¸éğÑHıwı>=f=HÛ³ğ,¥¨.à¹q€OV\nUñ’eÏJ‚ÃU4U~¯¶ÿ9™ude7ılªá„…}\ZSûy99i\00\r	*†H†÷\r\0\0kW\Z—d¾G1ıñI3½$ÜÈ1aRps½Ê”¯cıš_úÂqÉ¦]vuµòWÑ^½½(D¹öBÄ~uçÒE<…& ¢SŠ‡Íña—kë—\n6¬Gººgo8dÏ½BÓvú@´§õ(ã«\\Ùó~I7ıqA #–pì$ğà¯é´Q³\r\"Ğ£æÌz¿[3R8D','JKS','SUN','2012-03-21 11:41:07',2);
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
