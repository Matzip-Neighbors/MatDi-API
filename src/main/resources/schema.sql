DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
                        `user_id`	INT(11)	NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        `email`	VARCHAR(200)	NOT NULL,
                        `user_pwd`	VARCHAR(60)	NOT NULL,
                        `user_nm`	VARCHAR(200)	NOT NULL,
                        `user_prof_photo_path`	VARCHAR(1000)	NULL,
                        `user_reg_tp_cd`	VARCHAR(5)	NOT NULL	COMMENT '- 이메일 0010
- 카카오 0020',
                        `user_stat_cd`	VARCHAR(5)	NOT NULL	COMMENT '-가입 0010
-탈퇴 0020',
                        `admin_yn`	CHAR	NOT NULL	DEFAULT 'N',
                        `reg_dtm`	TIMESTAMP	NOT NULL	DEFAULT now(),
                        `last_login_dtm`	TIMESTAMP	NULL
);

DROP TABLE IF EXISTS `diary`;

CREATE TABLE `diary` (
                         `diary_id`	INT(11)	NOT NULL AUTO_INCREMENT PRIMARY KEY,
                         `diary_stat_cd`	VARCHAR(5)	NULL	COMMENT '-정상 0010
-보관 0020
-삭제 0030',
                         `diary_ctnt`	VARCHAR(4000)	NOT NULL,
                         `like_cnt`	NUMERIC	NOT NULL	DEFAULT 0,
                         `loc_id`	INT(11)	NOT NULL,
                         `loc_scre`	NUMERIC	NOT NULL	DEFAULT 0,
                         `cre_user_id`	INT(11)	NOT NULL,
                         `cre_dtm`	TIMESTAMP	NOT NULL	DEFAULT now(),
                         `mod_dtm`	TIMESTAMP	NULL
);

DROP TABLE IF EXISTS `loc`;

CREATE TABLE `loc` (
                       `loc_id`	INT(11)	NOT NULL AUTO_INCREMENT PRIMARY KEY,
                       `loc_nm`	VARCHAR(200)	NOT NULL,
                       `loc_addr`	VARCHAR(2000)	NOT NULL,
                       `loc_telno`	VARCHAR(50)	NULL,
                       `loc_thnl_url`	VARCHAR(1000)	NULL,
                       `kko_loc_id`	INT(11)	NOT NULL,
                       `loc_stat_cd`	VARCHAR(5)	NOT NULL	COMMENT '- 정상 0010
- 폐점 0020
- 삭제 0030',
                       `loc_tp_cd`	VARCHAR(5)	NOT NULL	COMMENT '-음식점 0010
-카페 0020',
                       `loc_lat`	NUMERIC	NOT NULL,
                       `loc_lon`	NUMERIC	NOT NULL,
                       `reg_user_id`	INT(11)	NOT NULL,
                       `loc_reg_dtm`	TIMESTAMP	NOT NULL	DEFAULT now(),
                       `loc_mod_dtm`	TIMESTAMP	NULL
);

DROP TABLE IF EXISTS `diary_photo`;

CREATE TABLE `diary_photo` (
                               `photo_seq`	INT(2)	NOT NULL,
                               `diary_id`	INT(11)	NOT NULL,
                               `photo_path`	VARCHAR(1000)	NOT NULL,
                               `photo_nm`	VARCHAR(200)	NOT NULL,
                               `photo_ext`	VARCHAR(10)	NOT NULL,
                               `photo_reg_dtm`	TIMESTAMP	NOT NULL	DEFAULT now()
);

DROP TABLE IF EXISTS `cmnt`;

CREATE TABLE `cmnt` (
                        `cmnt_id`	INT(11)	NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        `diary_id`	INT(11)	NOT NULL,
                        `cmnt_ctnt`	VARCHAR(4000)	NOT NULL,
                        `user_id`	INT(11)	NOT NULL,
                        `cmnt_cre_dtm`	TIMESTAMP	NOT NULL	DEFAULT now(),
                        `cmnt_mod_dtm`	TIMESTAMP	NULL
);

DROP TABLE IF EXISTS `htag`;

CREATE TABLE `htag` (
                        `htag_nm`	VARCHAR(200)	NOT NULL PRIMARY KEY,
                        `htag_reg_dtm`	TIMESTAMP	NOT NULL	DEFAULT now()
);

DROP TABLE IF EXISTS `diary_htag_rel`;

CREATE TABLE `diary_htag_rel` (
                                  `htag_nm`	VARCHAR(200)	NOT NULL,
                                  `diary_id`	INT(11)	NOT NULL
);

DROP TABLE IF EXISTS `like`;

CREATE TABLE `like` (
                        `user_id`	INT(11)	NOT NULL,
                        `diary_id`	INT(11)	NOT NULL,
                        `like_dtm`	TIMESTAMP	NOT NULL	DEFAULT now()
);

DROP TABLE IF EXISTS `verification`;

CREATE TABLE `verification` (
                                `vrf_id`	INT(11)	NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                `email`	VARCHAR(200)	NOT NULL,
                                `vrf_no`	VARCHAR(6)	NOT NULL,
                                `vrf_tp_cd`	VARCHAR(5)	NOT NULL	COMMENT '-이메일 0010
-문자 0020',
                                `vrf_stat_cd`	VARCHAR(5)	NOT NULL	COMMENT '-미인증 0010
-인중 0020
-인증실패 0030',
                                `expr_dtm`	TIMESTAMP	NOT NULL,
                                `cre_dtm`	TIMESTAMP	NOT NULL	DEFAULT now(),
                                `mod_dtm`	TIMESTAMP	NULL
);

ALTER TABLE `diary_photo` ADD CONSTRAINT `PK_DIARY_PHOTO` PRIMARY KEY (
                                                                       `photo_seq`,
                                                                       `diary_id`
    );

ALTER TABLE `diary_htag_rel` ADD CONSTRAINT `PK_DIARY_HTAG_REL` PRIMARY KEY (
                                                                             `htag_nm`,
                                                                             `diary_id`
    );

ALTER TABLE `like` ADD CONSTRAINT `PK_LIKE` PRIMARY KEY (
                                                         `user_id`,
                                                         `diary_id`
    );

ALTER TABLE `diary_photo` ADD CONSTRAINT `FK_diary_TO_diary_photo_1` FOREIGN KEY (
                                                                                  `diary_id`
    )
    REFERENCES `diary` (
                        `diary_id`
        );

ALTER TABLE `diary_htag_rel` ADD CONSTRAINT `FK_htag_TO_diary_htag_rel_1` FOREIGN KEY (
                                                                                       `htag_nm`
    )
    REFERENCES `htag` (
                       `htag_nm`
        );

ALTER TABLE `diary_htag_rel` ADD CONSTRAINT `FK_diary_TO_diary_htag_rel_1` FOREIGN KEY (
                                                                                        `diary_id`
    )
    REFERENCES `diary` (
                        `diary_id`
        );

ALTER TABLE `like` ADD CONSTRAINT `FK_user_TO_like_1` FOREIGN KEY (
                                                                   `user_id`
    )
    REFERENCES `user` (
                       `user_id`
        );

ALTER TABLE `like` ADD CONSTRAINT `FK_diary_TO_like_1` FOREIGN KEY (
                                                                    `diary_id`
    )
    REFERENCES `diary` (
                        `diary_id`
        );
