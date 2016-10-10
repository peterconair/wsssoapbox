-- MySQL dump 10.13  Distrib 5.5.9, for Win32 (x86)
--
-- Host: localhost    Database: wsssoapboxdb1
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
-- Current Database: `wsssoapboxdb1`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `wsssoapboxdb1` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `wsssoapboxdb1`;

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
INSERT INTO `key_store` VALUES (60,'client.jks','client_keystore','123456','ํํ\0\0\0\0\0\0\0\0\0\0server_cert\0\04f“eา\0X.509\0\0k0g0ะ N๓:&0\r	*H๗\r\00x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0\r111222140942Z\r120321140942Z0x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech00\r	*H๗\r\0\00\0~๑!,า้\Z!\r๙Eสjsถ\\#ฌ}ฏ\Z๎ถษ\rซ]ฬ\nฺนhฦๅเ๎ๅ”— hกT้Nน\0๎1@B–ษz๕L uฐ—Gr\n2๘eหะไ๘)ใt kVu๖5ฐ‘-Z$วJ{ษผ฿9f็…—ช/\00\r	*H๗\r\0\02Tขถ๑๗rิ}R2&“ฝใ\"ญ๐ผบCIท\Zfฺม\n:ฬY7ไ5็ัz(็ญ๐T#6mูm๖๋4ทnผkืd?็c\r1ฮn้,“pฌ}L}tาPำ!wNX;kIถI €’qA\\”๊\0ุ\0\0\0\0client cert\0\05@๚๚\0X.509\0\00}0ๆ N๓:ี0\r	*H๗\r\0010	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0\r111222141237Z\r120321141237Z010	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert00\r	*H๗\r\0\00\0ฆ_ฆ\n–่y<}bศ\\:่ฎDห็}\rุ๋M7Z3”ๅีฝSEึฤ๚๊๎KDakฌTธ้๐ัHw>=f=Hณ๐,ฅจ.เนq€OV\nU๑’eฯJรU4U~ฏถ9ude7lชแ…}\ZS๛y99i\00\r	*H๗\r\0\0kW\Z—dพG1๑I3ฝ$ศ1aRpsฝส”ฏc_๚ยqษฆ]vuต๒Wั^ฝฝ(Dน๖Bฤ~u็าE<…& ขSอ๑a—k๋—\n6ฌGบบgo8dฯฝBำv๚@ดง๕(ใซ\\ู๓~I7qA #–p์$๐\0\0\0\0client_keystore\0\04f่u\0\0บ0ถ0\n+*\0ข<ดโ$&  ปjงีญ’NTyeKค*i๐ไรW\\ิฦi>ฏ$ฺ๋/M?+yมญcueดณn)่…gล(ๅV[กJ\\๎ใ๔ฝU์ต<ฤ2y.…ษ๘ฉ\\’ศl๓}บจ$Oณ๗w๘ห#๒ฦeคy<,ฏ%๒ๆฯั;ื3๕#,—8๑hZา๕:฿[ะฅ 9W_ดฆ>ๆlลง&๖ฏ`Y$<U๋ญ้3๑y>๊=t\0ไ์2yw๗๙฿ช้S4ขขtฆภผศ2lบ•๎a<๘ป[%ลL;หชlู>=๐ จmAG4)+๒๕VงE{#bl”$ฺืฬZ,สCฃ<ฐT\05{w๑อๅw—HWmฐ]SVRฐh๕X^ธขดyูPm-vqt๖!ร็ฮำYฃYภ~*๋eไ<7‘vภไ4_ผ2ูฟฒVG`$Ko๚ฝe;	า๗ฎ[ฤูy tG“x๒2ปฑฆ๙ไd!๎ฃL7ฆฐธIT ํเ”HตฟI:}NมNkFว3๋~dU{•1F!jK4xY\rlบIJOฯ]ัOf\0ZXำม\nผเฃ“ซsี	’&pwLเ/๓4ำญเ๎{ชป|ฤฯ์rน)RมRkx๒@ฯRค่ไใา	๊๗คm<ฅ๛mB.	=ิEบ8lQ]ปJใแ็๎๚ฌS,f6m)ฉ1๐น}ฬziิ(ฬ40ฮ%๙ธฒ\"ตlฑwณ5€ข#ร-:บพ}rฤ๏**๓BNdฑHรu่ถ€w.ณKอ\'๘•|pf>๘ๆn~%็ฉฮgึ’\0\0\0\0X.509\0\00}0ๆ N๓:ี0\r	*H๗\r\0010	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0\r111222141237Z\r120321141237Z010	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert00\r	*H๗\r\0\00\0ฆ_ฆ\n–่y<}bศ\\:่ฎDห็}\rุ๋M7Z3”ๅีฝSEึฤ๚๊๎KDakฌTธ้๐ัHw>=f=Hณ๐,ฅจ.เนq€OV\nU๑’eฯJรU4U~ฏถ9ude7lชแ…}\ZS๛y99i\00\r	*H๗\r\0\0kW\Z—dพG1๑I3ฝ$ศ1aRpsฝส”ฏc_๚ยqษฆ]vuต๒Wั^ฝฝ(Dน๖Bฤ~u็าE<…& ขSอ๑a—k๋—\n6ฌGบบgo8dฯฝBำv๚@ดง๕(ใซ\\ู๓~I7qA #–p์$๐เฏ้ดQณ\r\"ะฃๆฬzฟ[3R8D','JKS','SUN','2012-02-03 06:25:39',2),(61,'server.jks','server_keystore','123456','ํํ\0\0\0\0\0\0\0\0\0\0client_cert\0\04f’>\0X.509\0\00}0ๆ N๓:ี0\r	*H๗\r\0010	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0\r111222141237Z\r120321141237Z010	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert00\r	*H๗\r\0\00\0ฆ_ฆ\n–่y<}bศ\\:่ฎDห็}\rุ๋M7Z3”ๅีฝSEึฤ๚๊๎KDakฌTธ้๐ัHw>=f=Hณ๐,ฅจ.เนq€OV\nU๑’eฯJรU4U~ฏถ9ude7lชแ…}\ZS๛y99i\00\r	*H๗\r\0\0kW\Z—dพG1๑I3ฝ$ศ1aRpsฝส”ฏc_๚ยqษฆ]vuต๒Wั^ฝฝ(Dน๖Bฤ~u็าE<…& ขSอ๑a—k๋—\n6ฌGบบgo8dฯฝBำv๚@ดง๕(ใซ\\ู๓~I7qA #–p์$๐\0\0\0\0server_keystore\0\04f[\0\0น0ต0\n+*\0ก>ั็ฌฝ“-ศZํzใT็ซๅกอm๖๓_้Jเวฦ๋จฎU๎—tpผ~วr…hิfF_y?oฎ ใชรๅใ\"๚ๅ๎ะำSฬWื(ฬวจZ]w\"\0_^ศ-o%พ,ฎM๋สคs \\”ณfํ๘e6Z-i‘ฮูำV๖ำ๏ ก)\0c้@•ก\ny— V7์7X M๖{๐“ื‘ิGQ:P.ฬ6๊\0ณX\n็ฦWธx<หt/ฺFกT€4;x8จ๋9nสXซWkv`n\0ส	S๓ูYbำ7\"นษกOzdธ\"kgฦg|€2น•เ}J_B๙YษuC฿\r|UU€๛ห็็4ํำNrฉี-\"ิา้I๊๗ใๅห>:v(=ฅJฺb๐์\"Q{G—ฒqGaHษห๒ธ4< yT+า๓฿R?R9`9รศ๕๛f่5(……PVฤ•ฤf\nD“ํ*ุ๐ฃM@ืูJ_u9gใ\r/ใG+S–แ\"21ฏ๓ะf%‘\nพ1ท`ำ;l~bผGiโ_‘+฿ฬrะjด	ดD\Z8&hj$๋[Okญส?`ซ.ปเxฦุ๛S“Hิ^2Fสc\rลๅ์ฬFยaห]^ฮ{2ฦๅตุNPz[ห0blฯษ\Zeฤฟะ็bนpฬบfผ&!วOพบT1๋๏้ผๅัภQ(9#%/ชD;ิฒu+ข๐“ะษ^\Z\rธๆ	ธQwNํlด\0ทรj…]i;)็->hา_C#:,ิฯ(๚\'3E่F\0\0\0\0X.509\0\0k0g0ะ N๓:&0\r	*H๗\r\00x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0\r111222140942Z\r120321140942Z0x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech00\r	*H๗\r\0\00\0~๑!,า้\Z!\r๙Eสjsถ\\#ฌ}ฏ\Z๎ถษ\rซ]ฬ\nฺนhฦๅเ๎ๅ”— hกT้Nน\0๎1@B–ษz๕L uฐ—Gr\n2๘eหะไ๘)ใt kVu๖5ฐ‘-Z$วJ{ษผ฿9f็…—ช/\00\r	*H๗\r\0\02Tขถ๑๗rิ}R2&“ฝใ\"ญ๐ผบCIท\Zfฺม\n:ฬY7ไ5็ัz(็ญ๐T#6mูm๖๋4ทnผkืd?็c\r1ฮn้,“pฌ}L}tาPำ!wNX;kIถI €’qA\\”๊\0ุ’tb8Rฉz๗uC’FY ๊','JKS','SUN','2012-02-03 22:54:59',2),(63,'server.jks','server_keystore','123456','ํํ\0\0\0\0\0\0\0\0\0\0client_cert\0\04f’>\0X.509\0\00}0ๆ N๓:ี0\r	*H๗\r\0010	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0\r111222141237Z\r120321141237Z010	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert00\r	*H๗\r\0\00\0ฆ_ฆ\n–่y<}bศ\\:่ฎDห็}\rุ๋M7Z3”ๅีฝSEึฤ๚๊๎KDakฌTธ้๐ัHw>=f=Hณ๐,ฅจ.เนq€OV\nU๑’eฯJรU4U~ฏถ9ude7lชแ…}\ZS๛y99i\00\r	*H๗\r\0\0kW\Z—dพG1๑I3ฝ$ศ1aRpsฝส”ฏc_๚ยqษฆ]vuต๒Wั^ฝฝ(Dน๖Bฤ~u็าE<…& ขSอ๑a—k๋—\n6ฌGบบgo8dฯฝBำv๚@ดง๕(ใซ\\ู๓~I7qA #–p์$๐\0\0\0\0server_keystore\0\04f[\0\0น0ต0\n+*\0ก>ั็ฌฝ“-ศZํzใT็ซๅกอm๖๓_้Jเวฦ๋จฎU๎—tpผ~วr…hิfF_y?oฎ ใชรๅใ\"๚ๅ๎ะำSฬWื(ฬวจZ]w\"\0_^ศ-o%พ,ฎM๋สคs \\”ณfํ๘e6Z-i‘ฮูำV๖ำ๏ ก)\0c้@•ก\ny— V7์7X M๖{๐“ื‘ิGQ:P.ฬ6๊\0ณX\n็ฦWธx<หt/ฺFกT€4;x8จ๋9nสXซWkv`n\0ส	S๓ูYbำ7\"นษกOzdธ\"kgฦg|€2น•เ}J_B๙YษuC฿\r|UU€๛ห็็4ํำNrฉี-\"ิา้I๊๗ใๅห>:v(=ฅJฺb๐์\"Q{G—ฒqGaHษห๒ธ4< yT+า๓฿R?R9`9รศ๕๛f่5(……PVฤ•ฤf\nD“ํ*ุ๐ฃM@ืูJ_u9gใ\r/ใG+S–แ\"21ฏ๓ะf%‘\nพ1ท`ำ;l~bผGiโ_‘+฿ฬrะjด	ดD\Z8&hj$๋[Okญส?`ซ.ปเxฦุ๛S“Hิ^2Fสc\rลๅ์ฬFยaห]^ฮ{2ฦๅตุNPz[ห0blฯษ\Zeฤฟะ็bนpฬบfผ&!วOพบT1๋๏้ผๅัภQ(9#%/ชD;ิฒu+ข๐“ะษ^\Z\rธๆ	ธQwNํlด\0ทรj…]i;)็->hา_C#:,ิฯ(๚\'3E่F\0\0\0\0X.509\0\0k0g0ะ N๓:&0\r	*H๗\r\00x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0\r111222140942Z\r120321140942Z0x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech00\r	*H๗\r\0\00\0~๑!,า้\Z!\r๙Eสjsถ\\#ฌ}ฏ\Z๎ถษ\rซ]ฬ\nฺนhฦๅเ๎ๅ”— hกT้Nน\0๎1@B–ษz๕L uฐ—Gr\n2๘eหะไ๘)ใt kVu๖5ฐ‘-Z$วJ{ษผ฿9f็…—ช/\00\r	*H๗\r\0\02Tขถ๑๗rิ}R2&“ฝใ\"ญ๐ผบCIท\Zfฺม\n:ฬY7ไ5็ัz(็ญ๐T#6mูm๖๋4ทnผkืd?็c\r1ฮn้,“pฌ}L}tาPำ!wNX;kIถI €’qA\\”๊\0ุ’tb8Rฉz๗uC’FY ๊','JKS','SUN','2012-02-03 23:56:29',4),(64,'default.jks','default_keytore','123456','ํํ\0\0\0\0\0\0\0\0\0\0client_cert\0\052ฬอ\0X.509\0\00}0ๆ N๓:ี0\r	*H๗\r\0010	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0\r111222141237Z\r120321141237Z010	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert00\r	*H๗\r\0\00\0ฆ_ฆ\n–่y<}bศ\\:่ฎDห็}\rุ๋M7Z3”ๅีฝSEึฤ๚๊๎KDakฌTธ้๐ัHw>=f=Hณ๐,ฅจ.เนq€OV\nU๑’eฯJรU4U~ฏถ9ude7lชแ…}\ZS๛y99i\00\r	*H๗\r\0\0kW\Z—dพG1๑I3ฝ$ศ1aRpsฝส”ฏc_๚ยqษฆ]vuต๒Wั^ฝฝ(Dน๖Bฤ~u็าE<…& ขSอ๑a—k๋—\n6ฌGบบgo8dฯฝBำv๚@ดง๕(ใซ\\ู๓~I7qA #–p์$๐\0\0\0\0server_cert\0\052“ศ\0X.509\0\0k0g0ะ N๓:&0\r	*H๗\r\00x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0\r111222140942Z\r120321140942Z0x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech00\r	*H๗\r\0\00\0~๑!,า้\Z!\r๙Eสjsถ\\#ฌ}ฏ\Z๎ถษ\rซ]ฬ\nฺนhฦๅเ๎ๅ”— hกT้Nน\0๎1@B–ษz๕L uฐ—Gr\n2๘eหะไ๘)ใt kVu๖5ฐ‘-Z$วJ{ษผ฿9f็…—ช/\00\r	*H๗\r\0\02Tขถ๑๗rิ}R2&“ฝใ\"ญ๐ผบCIท\Zfฺม\n:ฬY7ไ5็ัz(็ญ๐T#6mูm๖๋4ทnผkืd?็c\r1ฮn้,“pฌ}L}tาPำ!wNX;kIถI €’qA\\”๊\0ุ\0\0\0\0default_keytore\0\052#\0\000\n+*\0๊9่81ฌน`~ไ#Pภe_ผB`<ๅษ๑ที-Uni=ว๔ฌษษฟฒ6ํ2xY๊6Wว๘ฏฏHZ*[wrb\n/ษเa|่yb ZlนcJ*4A๙ล\nจ๚NฮZ%J)m	W?)ูL AKJ๛mขhจQฉ…ท๎s1tฬ๕ไซึ์\'จเ>๕แฺ่Cบ๐QุZlฮ0,1แ\"R9…{ๆHt๒\0ฒIFBัห!;ํฆ“q.E๎jฮบ<ำ๚/m๑ฉฌwSFC1.ผ|sษ์/&<UG๕HHA#•ฬโi์ต<ฃ฿ JYแ”)1n฿Cด…ฝฒ=W\'>ณดaodฺU๐?\rฤ\0คด•jเ&๖3_ฒ;ำJ:ำชbyeชydAK!/๐ีพฑl0M๖สน5$์z>ฯณ๚ป•ฅ3pคึย:?ฃ็%Kซ‘ข=ภMูmnr[๔ะU๏~เF)”{ บฑx$‘).ฎธคnกYบฮโฒRฌนY”ฺหZาส4ฏT๏ิ\rt๔ผ๒,ฃ;ยยฌ\"\"ห่๋ใะf€\nป‘ดJฟ	rช๋ู๖v็bร%zVไ$พW๗ๅ€|ฝ-ุ—*’฿เo–เI1bๆD‘C“BพU%kqใL>๓T๏\nฺyอ•ก8|N,ว๔ี‘ธkF {OtfKZ่rด[\rิUุX-ษ?j“๑;4เพลDท7No$dธซ็Tn<tเLfต-Q๘ฤษ^ฉ€@ฐG.lส@ะุๆ`Uฬ1@Kืb๛—๙ธE&m\n$EษJ{ฐ.๕s5{<Sผ~ู~d๒>ฅmฐฝ&i}h‘ศzxs@๘ฯ๊ลu&๒•IถLบRณใั๓GCรBnรS6Kฉ€LX๋\nr์\"E\\๖ฮฐ-๑kนพF…}‘RชฝสฤHฏแO8ฝ…@3ฑ…JูHฝซ6p\Z้ไ…\nB’Sฤำ6\"ตบจฏ~x&ศฯELR8บ4h6{…็N3[ํบ\Z5yจ\\ฝ*๗Fษ<ๅฉ๎ป๚ธ35gโฐe\"ถ๔Jๆ7ซร\0Ys๖k็s๐ล๏ZsNฎ์nบDม\\Eณ1฿พ0ีษ\nฤเถแใY(ณj4“6ื—ผึ9ว[nJขrลร~_|ฝ~ิ๔ฅบgัQ|นฎ.I็(7k#ึๆผm	g8d ัขF…ตต4€ํ้s\\ณV\nไขฆขBไ6ภo#J4นมh’9๛C]ไ_2ถ@ซำ!ํLืก-%ษ ฮ\\ึ๒o๚=ผ7๙H#ศ)G/aหXฮX:(7๖๔ฮ8\'5ลqK[ฯผขp2{aซ;–rด9ธ๏•q+บ๗ฦMึ\n-๔j+ไ\0/XX๗”\ZAแ=ธ4๚บทใ\nสZ\'็จj๏D–VpฃสทhTY“ิ9า-$o฿ซ!}ฌ0\'c\\ุ๐livb:bYทุ9ณฦอLฆ$ฟX๙๙– ฝ++\r๐jyสD๙\0\0\0\0X.509\0\0ณ0ฏ0—O\'”\Z0\r	*H๗\r\001%0#	*H๗\r	Default@wsssoapbox.org10	UTH10U\nDefault ST10U	Default L10U\n	Default O10U\nDefault OU10U\nDefault CN0\r120131071122Z\r130130071122Z01%0#	*H๗\r	Default@wsssoapbox.org10	UTH10U\nDefault ST10U	Default L10U\n	Default O10U\nDefault OU10U\nDefault CN0\"0\r	*H๗\r\0\00\n\0ล-Kโผ\ZoCDKถ๕ฤยลNฦt5 ่พ๊j‘atๅ€{อ\\cื฿ฐ&บ_ oผืL0`8,nืพ|น{ษ๗ธ$GฯใพY๙ฅZรำษM๕ฐdUฝi++ะ[๛i&เ7Jใม…ไื฿\\1ฅ…ฏอ38ฎซฆู๊;มม*๖ใy,Z{พึ(2o%ดถfฬ^ูBL5ำkำJฆญ5ำึ;๖้T=uYุำแ5ฦๆา๎บใฃผาๅi_–วไ{ฮvA2Q€ลPJoEะคิ#งึจฺ hเrx8มงทำ๑naนฦใ\00\r	*H๗\r\0\0ฦฯ๐ธvJฃ๖(1o\'ฃฮgkสo6๗cน๚“”g)MbxCึำH๎Cษ–ฉำ	AP)฿Rั.ฏ ืณq–ฒฮl๏ฐั”\r:u๛ZPบ๎M@L๔ฆIO\nฦึุ_^หOลหุญ๛“๐\nื๑>@N ^Fpฺ	<๏c/(\'\reo$—้ค่TNโ…์็ ฝง€g+Ilุ\ZธนำฑVfp่8]ฮLUG.C^&wb์=จ;I(B๛—ณfฺ€B9Lฆ๙ฤ๓Sฌ-๏ฅซภT({นๆฬฏฝ๎)ฒ.ูณ~โa๒-','JKS','SUN','2012-02-04 00:01:56',4),(68,'server.jks','server_keystore','123456','ํํ\0\0\0\0\0\0\0\0\0\0client_cert\0\04f’>\0X.509\0\00}0ๆ N๓:ี0\r	*H๗\r\0010	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0\r111222141237Z\r120321141237Z010	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert00\r	*H๗\r\0\00\0ฆ_ฆ\n–่y<}bศ\\:่ฎDห็}\rุ๋M7Z3”ๅีฝSEึฤ๚๊๎KDakฌTธ้๐ัHw>=f=Hณ๐,ฅจ.เนq€OV\nU๑’eฯJรU4U~ฏถ9ude7lชแ…}\ZS๛y99i\00\r	*H๗\r\0\0kW\Z—dพG1๑I3ฝ$ศ1aRpsฝส”ฏc_๚ยqษฆ]vuต๒Wั^ฝฝ(Dน๖Bฤ~u็าE<…& ขSอ๑a—k๋—\n6ฌGบบgo8dฯฝBำv๚@ดง๕(ใซ\\ู๓~I7qA #–p์$๐\0\0\0\0server_keystore\0\04f[\0\0น0ต0\n+*\0ก>ั็ฌฝ“-ศZํzใT็ซๅกอm๖๓_้Jเวฦ๋จฎU๎—tpผ~วr…hิfF_y?oฎ ใชรๅใ\"๚ๅ๎ะำSฬWื(ฬวจZ]w\"\0_^ศ-o%พ,ฎM๋สคs \\”ณfํ๘e6Z-i‘ฮูำV๖ำ๏ ก)\0c้@•ก\ny— V7์7X M๖{๐“ื‘ิGQ:P.ฬ6๊\0ณX\n็ฦWธx<หt/ฺFกT€4;x8จ๋9nสXซWkv`n\0ส	S๓ูYbำ7\"นษกOzdธ\"kgฦg|€2น•เ}J_B๙YษuC฿\r|UU€๛ห็็4ํำNrฉี-\"ิา้I๊๗ใๅห>:v(=ฅJฺb๐์\"Q{G—ฒqGaHษห๒ธ4< yT+า๓฿R?R9`9รศ๕๛f่5(……PVฤ•ฤf\nD“ํ*ุ๐ฃM@ืูJ_u9gใ\r/ใG+S–แ\"21ฏ๓ะf%‘\nพ1ท`ำ;l~bผGiโ_‘+฿ฬrะjด	ดD\Z8&hj$๋[Okญส?`ซ.ปเxฦุ๛S“Hิ^2Fสc\rลๅ์ฬFยaห]^ฮ{2ฦๅตุNPz[ห0blฯษ\Zeฤฟะ็bนpฬบfผ&!วOพบT1๋๏้ผๅัภQ(9#%/ชD;ิฒu+ข๐“ะษ^\Z\rธๆ	ธQwNํlด\0ทรj…]i;)็->hา_C#:,ิฯ(๚\'3E่F\0\0\0\0X.509\0\0k0g0ะ N๓:&0\r	*H๗\r\00x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0\r111222140942Z\r120321140942Z0x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech00\r	*H๗\r\0\00\0~๑!,า้\Z!\r๙Eสjsถ\\#ฌ}ฏ\Z๎ถษ\rซ]ฬ\nฺนhฦๅเ๎ๅ”— hกT้Nน\0๎1@B–ษz๕L uฐ—Gr\n2๘eหะไ๘)ใt kVu๖5ฐ‘-Z$วJ{ษผ฿9f็…—ช/\00\r	*H๗\r\0\02Tขถ๑๗rิ}R2&“ฝใ\"ญ๐ผบCIท\Zfฺม\n:ฬY7ไ5็ัz(็ญ๐T#6mูm๖๋4ทnผkืd?็c\r1ฮn้,“pฌ}L}tาPำ!wNX;kIถI €’qA\\”๊\0ุ’tb8Rฉz๗uC’FY ๊','JKS','SUN','2012-02-04 08:59:00',1),(71,'client.jks','client_keystore','123456','ํํ\0\0\0\0\0\0\0\0\0\0server_cert\0\04f“eา\0X.509\0\0k0g0ะ N๓:&0\r	*H๗\r\00x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0\r111222140942Z\r120321140942Z0x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech00\r	*H๗\r\0\00\0~๑!,า้\Z!\r๙Eสjsถ\\#ฌ}ฏ\Z๎ถษ\rซ]ฬ\nฺนhฦๅเ๎ๅ”— hกT้Nน\0๎1@B–ษz๕L uฐ—Gr\n2๘eหะไ๘)ใt kVu๖5ฐ‘-Z$วJ{ษผ฿9f็…—ช/\00\r	*H๗\r\0\02Tขถ๑๗rิ}R2&“ฝใ\"ญ๐ผบCIท\Zfฺม\n:ฬY7ไ5็ัz(็ญ๐T#6mูm๖๋4ทnผkืd?็c\r1ฮn้,“pฌ}L}tาPำ!wNX;kIถI €’qA\\”๊\0ุ\0\0\0\0client cert\0\05@๚๚\0X.509\0\00}0ๆ N๓:ี0\r	*H๗\r\0010	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0\r111222141237Z\r120321141237Z010	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert00\r	*H๗\r\0\00\0ฆ_ฆ\n–่y<}bศ\\:่ฎDห็}\rุ๋M7Z3”ๅีฝSEึฤ๚๊๎KDakฌTธ้๐ัHw>=f=Hณ๐,ฅจ.เนq€OV\nU๑’eฯJรU4U~ฏถ9ude7lชแ…}\ZS๛y99i\00\r	*H๗\r\0\0kW\Z—dพG1๑I3ฝ$ศ1aRpsฝส”ฏc_๚ยqษฆ]vuต๒Wั^ฝฝ(Dน๖Bฤ~u็าE<…& ขSอ๑a—k๋—\n6ฌGบบgo8dฯฝBำv๚@ดง๕(ใซ\\ู๓~I7qA #–p์$๐\0\0\0\0client_keystore\0\04f่u\0\0บ0ถ0\n+*\0ข<ดโ$&  ปjงีญ’NTyeKค*i๐ไรW\\ิฦi>ฏ$ฺ๋/M?+yมญcueดณn)่…gล(ๅV[กJ\\๎ใ๔ฝU์ต<ฤ2y.…ษ๘ฉ\\’ศl๓}บจ$Oณ๗w๘ห#๒ฦeคy<,ฏ%๒ๆฯั;ื3๕#,—8๑hZา๕:฿[ะฅ 9W_ดฆ>ๆlลง&๖ฏ`Y$<U๋ญ้3๑y>๊=t\0ไ์2yw๗๙฿ช้S4ขขtฆภผศ2lบ•๎a<๘ป[%ลL;หชlู>=๐ จmAG4)+๒๕VงE{#bl”$ฺืฬZ,สCฃ<ฐT\05{w๑อๅw—HWmฐ]SVRฐh๕X^ธขดyูPm-vqt๖!ร็ฮำYฃYภ~*๋eไ<7‘vภไ4_ผ2ูฟฒVG`$Ko๚ฝe;	า๗ฎ[ฤูy tG“x๒2ปฑฆ๙ไd!๎ฃL7ฆฐธIT ํเ”HตฟI:}NมNkFว3๋~dU{•1F!jK4xY\rlบIJOฯ]ัOf\0ZXำม\nผเฃ“ซsี	’&pwLเ/๓4ำญเ๎{ชป|ฤฯ์rน)RมRkx๒@ฯRค่ไใา	๊๗คm<ฅ๛mB.	=ิEบ8lQ]ปJใแ็๎๚ฌS,f6m)ฉ1๐น}ฬziิ(ฬ40ฮ%๙ธฒ\"ตlฑwณ5€ข#ร-:บพ}rฤ๏**๓BNdฑHรu่ถ€w.ณKอ\'๘•|pf>๘ๆn~%็ฉฮgึ’\0\0\0\0X.509\0\00}0ๆ N๓:ี0\r	*H๗\r\0010	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0\r111222141237Z\r120321141237Z010	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert00\r	*H๗\r\0\00\0ฆ_ฆ\n–่y<}bศ\\:่ฎDห็}\rุ๋M7Z3”ๅีฝSEึฤ๚๊๎KDakฌTธ้๐ัHw>=f=Hณ๐,ฅจ.เนq€OV\nU๑’eฯJรU4U~ฏถ9ude7lชแ…}\ZS๛y99i\00\r	*H๗\r\0\0kW\Z—dพG1๑I3ฝ$ศ1aRpsฝส”ฏc_๚ยqษฆ]vuต๒Wั^ฝฝ(Dน๖Bฤ~u็าE<…& ขSอ๑a—k๋—\n6ฌGบบgo8dฯฝBำv๚@ดง๕(ใซ\\ู๓~I7qA #–p์$๐เฏ้ดQณ\r\"ะฃๆฬzฟ[3R8D','JKS','SUN','2012-02-07 08:13:53',1),(72,'too.jks',NULL,'123456','ํํ\0\0\0\0\0\0\0็ -โ8าช|8j_๎-*','JKS','SUN','2012-03-21 11:40:49',2),(73,'client.jks','client_keystore','123456','ํํ\0\0\0\0\0\0\0\0\0\0server_cert\0\04f“eา\0X.509\0\0k0g0ะ N๓:&0\r	*H๗\r\00x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech0\r111222140942Z\r120321140942Z0x10	UTH10U\nJava State10U	Java City10U\n	Java Org.10UJava Department10U	Java Tech00\r	*H๗\r\0\00\0~๑!,า้\Z!\r๙Eสjsถ\\#ฌ}ฏ\Z๎ถษ\rซ]ฬ\nฺนhฦๅเ๎ๅ”— hกT้Nน\0๎1@B–ษz๕L uฐ—Gr\n2๘eหะไ๘)ใt kVu๖5ฐ‘-Z$วJ{ษผ฿9f็…—ช/\00\r	*H๗\r\0\02Tขถ๑๗rิ}R2&“ฝใ\"ญ๐ผบCIท\Zfฺม\n:ฬY7ไ5็ัz(็ญ๐T#6mูm๖๋4ทnผkืd?็c\r1ฮn้,“pฌ}L}tาPำ!wNX;kIถI €’qA\\”๊\0ุ\0\0\0\0client cert\0\05@๚๚\0X.509\0\00}0ๆ N๓:ี0\r	*H๗\r\0010	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0\r111222141237Z\r120321141237Z010	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert00\r	*H๗\r\0\00\0ฆ_ฆ\n–่y<}bศ\\:่ฎDห็}\rุ๋M7Z3”ๅีฝSEึฤ๚๊๎KDakฌTธ้๐ัHw>=f=Hณ๐,ฅจ.เนq€OV\nU๑’eฯJรU4U~ฏถ9ude7lชแ…}\ZS๛y99i\00\r	*H๗\r\0\0kW\Z—dพG1๑I3ฝ$ศ1aRpsฝส”ฏc_๚ยqษฆ]vuต๒Wั^ฝฝ(Dน๖Bฤ~u็าE<…& ขSอ๑a—k๋—\n6ฌGบบgo8dฯฝBำv๚@ดง๕(ใซ\\ู๓~I7qA #–p์$๐\0\0\0\0client_keystore\0\04f่u\0\0บ0ถ0\n+*\0ข<ดโ$&  ปjงีญ’NTyeKค*i๐ไรW\\ิฦi>ฏ$ฺ๋/M?+yมญcueดณn)่…gล(ๅV[กJ\\๎ใ๔ฝU์ต<ฤ2y.…ษ๘ฉ\\’ศl๓}บจ$Oณ๗w๘ห#๒ฦeคy<,ฏ%๒ๆฯั;ื3๕#,—8๑hZา๕:฿[ะฅ 9W_ดฆ>ๆlลง&๖ฏ`Y$<U๋ญ้3๑y>๊=t\0ไ์2yw๗๙฿ช้S4ขขtฆภผศ2lบ•๎a<๘ป[%ลL;หชlู>=๐ จmAG4)+๒๕VงE{#bl”$ฺืฬZ,สCฃ<ฐT\05{w๑อๅw—HWmฐ]SVRฐh๕X^ธขดyูPm-vqt๖!ร็ฮำYฃYภ~*๋eไ<7‘vภไ4_ผ2ูฟฒVG`$Ko๚ฝe;	า๗ฎ[ฤูy tG“x๒2ปฑฆ๙ไd!๎ฃL7ฆฐธIT ํเ”HตฟI:}NมNkFว3๋~dU{•1F!jK4xY\rlบIJOฯ]ัOf\0ZXำม\nผเฃ“ซsี	’&pwLเ/๓4ำญเ๎{ชป|ฤฯ์rน)RมRkx๒@ฯRค่ไใา	๊๗คm<ฅ๛mB.	=ิEบ8lQ]ปJใแ็๎๚ฌS,f6m)ฉ1๐น}ฬziิ(ฬ40ฮ%๙ธฒ\"ตlฑwณ5€ข#ร-:บพ}rฤ๏**๓BNdฑHรu่ถ€w.ณKอ\'๘•|pf>๘ๆn~%็ฉฮgึ’\0\0\0\0X.509\0\00}0ๆ N๓:ี0\r	*H๗\r\0010	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert0\r111222141237Z\r120321141237Z010	UTH10UClient State10UClient City10U\nClient Org.1\Z0UClient Department10UClient Cert00\r	*H๗\r\0\00\0ฆ_ฆ\n–่y<}bศ\\:่ฎDห็}\rุ๋M7Z3”ๅีฝSEึฤ๚๊๎KDakฌTธ้๐ัHw>=f=Hณ๐,ฅจ.เนq€OV\nU๑’eฯJรU4U~ฏถ9ude7lชแ…}\ZS๛y99i\00\r	*H๗\r\0\0kW\Z—dพG1๑I3ฝ$ศ1aRpsฝส”ฏc_๚ยqษฆ]vuต๒Wั^ฝฝ(Dน๖Bฤ~u็าE<…& ขSอ๑a—k๋—\n6ฌGบบgo8dฯฝBำv๚@ดง๕(ใซ\\ู๓~I7qA #–p์$๐เฏ้ดQณ\r\"ะฃๆฬzฟ[3R8D','JKS','SUN','2012-03-21 11:41:07',2);
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
-- Current Database: `wsssoapboxdb1`
--

USE `wsssoapboxdb1`;

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

-- Dump completed on 2012-03-24  0:37:28
