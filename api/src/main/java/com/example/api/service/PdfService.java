package com.example.api.service;

import com.example.api.dto.CoveragesDTO;
import com.example.api.dto.htmltopdf.AddrDTO;
import com.example.api.dto.htmltopdf.CoInfoDTO;
import com.example.api.dto.htmltopdf.PoInfoDTO;
import com.example.api.dto.htmltopdf.PolicyDTO;
import com.example.api.util.ExportPdfUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class PdfService {

    @Autowired
    private TemplateEngine templateEngine;

    public byte[] generatePolicyPdf() {
        // 模擬資料
        // 基本資料
        String names = "測試員 A123456789";
        String sex = "男性";
        Integer age = 25;
        // 聯絡資料
        List<AddrDTO> addrList = new ArrayList<>();
        for (int i = 1 ; i <= 10 ; i++) {
            AddrDTO addr = new AddrDTO();
            addr.setAddrInd(String.valueOf(i));
            addr.setAddress("台北市內湖區石潭路58號"+i+"樓");
            addr.setTel("02-23455511");
            addrList.add(addr);
        }
        // 保單資料
        List<PolicyDTO> policyList = new ArrayList<>();
        for (int i = 0 ; i < 5 ; i++) {
            PolicyDTO policyDTO = new PolicyDTO();
            // 保單
            PoInfoDTO poInfo = new PoInfoDTO();
            poInfo.setPolicyNo("10000000000"+i);
            poInfo.setPoStsCode("42");
            poInfo.setPoIssueDate("100/01/10");
            poInfo.setPaidToDate("115/01/10");
            poInfo.setClaimInd("N");
            poInfo.setRemarkInd("N");
            poInfo.setInformInd("N");
            poInfo.setWeakInd("N");
            policyDTO.setPoInfo(poInfo);
            // 保障
            List<CoInfoDTO> coInfoList = new ArrayList<>();
            for (int j = 1 ; j <= 3 ; j++) {
                CoInfoDTO coInfo = new CoInfoDTO();
                coInfo.setClientIdent("I1");
                coInfo.setPlanCode("ABCD"+i);
                coInfo.setRateScale("0");
                coInfo.setFaceAmt(1000000.00);
                coInfo.setCoIssueDate("100/01/10");
                coInfo.setCoChangeDate("100/01/20");
                coInfoList.add(coInfo);
            }
            policyDTO.setCoInfoList(coInfoList);
            policyList.add(policyDTO);
        }

        // 設定變數
        Context context = new Context();
        context.setVariable("names", names);
        context.setVariable("sex", sex);
        context.setVariable("age", age);
        context.setVariable("addrList", addrList);
        context.setVariable("policyList", policyList);

        return ExportPdfUtil.htmlToPdf(templateEngine, "客戶資料表.html", context);
    }
}
