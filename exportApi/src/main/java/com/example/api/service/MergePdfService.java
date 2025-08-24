package com.example.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.util.MergePdrUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class MergePdfService {
    @Autowired
    private HtmlToPdfService htmlToPdfService;

    public byte[] mergePDF() {
        List<byte[]> fileList = new ArrayList<>();

        for (int i = 1 ; i <= 5 ; i++) {
            byte[] file = htmlToPdfService.generatePdf();
            fileList.add(file);
        }

        return MergePdrUtil.mergePDF(fileList);
    }
}
