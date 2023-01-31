package com.zhl.redis.stream.qps.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * <h1>构建基础数据</h1>
 */
@Slf4j
@Component
public class BuildService {

    static String jsonModel = "{\"opter_name\":\"终端收费员\",\"input\":{\"mdtrtinfo\":{\"psn_no\":\"21040000000021040110093185\",\"coner_name\":\"\",\"tel\":\"\",\"begntime\":\"2022-12-07 09:19:05\",\"med_type\":\"21\",\"ipt_no\":\"ZY20221208001\",\"medrcdno\":\"\",\"atddr_no\":\"D211281003674\",\"chfpdr_name\":\"杨爱民\",\"adm_diag_dscr\":\"癫痫综合征\",\"adm_dept_codg\":\"A07\",\"adm_dept_name\":\"儿科\",\"adm_bed\":\"1493084294610976\",\"dscg_maindiag_code\":\"G40.900\",\"dscg_maindiag_name\":\"癫痫综合征\",\"main_cond_dscr\":\"\",\"dise_codg\":\"\",\"dise_name\":\"\",\"oprn_oprt_code\":\"\",\"oprn_oprt_name\":\"\",\"fpsc_no\":\"\",\"matn_type\":\"\",\"birctrl_type\":\"\",\"latechb_flag\":\"\",\"geso_val\":\"\",\"fetts\":\"\",\"fetus_cnt\":\"\",\"pret_flag\":\"\",\"birctrl_matn_date\":\"\",\"dise_type_code\":\"\",\"exp_content\":{\"local_dise\":\"\"},\"insutype\":\"390\",\"mdtrt_cert_type\":\"03\",\"mdtrt_cert_no\":\"DA7187489|MaiwAPaxJ/kBv02Dg5oswUU0QqQUDisV2q998NUGrM/ckNWJmlA2cKNLcJBZNjJRwMNA4bcGUiMk4/5KszfVPgimO3sOB99O2dNiAXowaVlqcJ/it4Dqulfz9ar6BvvuBtIDgIA1khRCcbxQ5pYQ8KvZrmYUoWLXQpXNYaqtv1PuBQH8xOOr90c6Yc3UnZNV9EVytewPe8VYZH4lXLWNmiHa53nxapIXeQ/jEyiruTDSo1FuZS9b6izthsFg3ahP\"},\"diseinfo\":{\"psn_no\":\"21040000000021040110093185\",\"diag_type\":\"1\",\"maindiag_flag\":\"1\",\"diag_srt_no\":\"1\",\"diag_code\":\"G40.900\",\"diag_name\":\"癫痫综合征\",\"adm_cond\":\"癫痫综合征\",\"diag_dept\":\"儿科\",\"dise_dor_no\":\"D210102015489\",\"dise_dor_name\":\"曲凤媛\",\"diag_time\":\"2022-12-07 09:19:05\"}},\"fixmedins_name\":\"沈阳六一儿童医院\",\"sign_no\":\"0000\",\"opter\":\"0000\",\"fixmedins_code\":\"H21010200144\",\"infno\":\"2401\",\"dev_no\":\"\",\"dev_safe_info\":\"\",\"inf_time\":\"2022-12-11 15:44:03\",\"recer_sys_code\":\"YMXCHIS\",\"insuplc_admdvs\":\"210400\",\"infver\":\"V1.0\",\"opter_type\":\"1\",\"cainfo\":\"046dbdaed2fc2e01c09a7427669c4ec2224d35db3c13d3f76bfa8459773e21bf6c98c002c3949da5b26fa6978b1a5c8ff8023e0078d09a9089e1fbf6de993633c6aca0a9c665618281a4b86b7fc696bc4843742766af6effaf6aa0d693dafbac4c7224ff5ec4a96f3300fa8f958e22586db776544c1495db18a4a43dc68c55f9e3\",\"signtype\":\"\",\"msgid\":\"20221211154403240156\",\"mdtrtarea_admvs\":\"210100\"}";

    static Long jsonNums = 30000L;

    /**
     * <h2>根据入参构建从数据</h2>
     * TODO 多此一举   我真是个dsb
     */
    public List<Map> buildData(String jsonModel, Long jsonNums) {
        List<Map> data = new ArrayList();
//        AtomicInteger index = new AtomicInteger();
        if (!Optional.ofNullable(jsonModel).isPresent()) {
            jsonModel = BuildService.jsonModel;
        }
        if (!Optional.ofNullable(jsonNums).isPresent()) {
            jsonNums = BuildService.jsonNums;
        }
        String finalJsonModel = jsonModel;
        Optional.ofNullable(jsonNums).ifPresent(nums -> {
//            new Thread(() -> {
            for (int i = 0; i < nums; i++) {
                Map map = new HashMap();
                map.put((i + 1) + "", finalJsonModel);
                data.add(map);
            }
//            }, "创建数据线程-01").start();
        });
        return data;
    }


}
