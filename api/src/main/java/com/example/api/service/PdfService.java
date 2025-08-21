package com.example.api.service;

import com.example.api.dto.CoveragesDTO;
import com.example.api.dto.PolicyDTO;
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
        PolicyDTO policyDTO = new PolicyDTO();
        policyDTO.setPolicyNo("100000000012");
        policyDTO.setPoStsCode("42 繳費中");
        policyDTO.setO1Name("貂蟬");
        policyDTO.setI1Name("呂布");
        policyDTO.setPoIssueDate("114/01/01");

        List<CoveragesDTO> coList = new ArrayList<>();
        for (int i = 1 ; i <= 50 ; i++) {
            CoveragesDTO coveragesDTO = new CoveragesDTO();
            coveragesDTO.setCoverageNo(i);
            coveragesDTO.setPlanCode("A"+i);
            coveragesDTO.setRateScale("0");
            coveragesDTO.setFaceAmt(1000000.00);
            coList.add(coveragesDTO);
        }
        policyDTO.setCoList(coList);

        Context context = new Context();
        context.setVariable("policyNo", policyDTO.getPolicyNo());
        context.setVariable("poStsCode", policyDTO.getPoStsCode());
        context.setVariable("o1Name", policyDTO.getO1Name());
        context.setVariable("i1Name", policyDTO.getI1Name());
        context.setVariable("poIssueDate", policyDTO.getPoIssueDate());
        context.setVariable("coverages", policyDTO.getCoList());

        return ExportPdfUtil.htmlToPdf(templateEngine, "policy.html", context);
    }

}
