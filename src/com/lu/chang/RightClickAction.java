package com.lu.chang;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class RightClickAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        String title = "Export File!";

        VirtualFile[] data = e.getData(CommonDataKeys.VIRTUAL_FILE_ARRAY);

        Project project = e.getData(CommonDataKeys.PROJECT);

        String basePath = project.getBasePath() + "/out";

        StringBuffer message = new StringBuffer();
        for (VirtualFile file : data) {
            try {
                String path = file.getPath();
                int comIndex = path.indexOf("/com/");
                int dirIndex = path.lastIndexOf("/");
                message.append(path.substring(dirIndex + 1) + ",");
                String fileDir = basePath + path.substring(comIndex, dirIndex);
                String filePath = basePath + path.substring(comIndex);
                File dir = new File(fileDir);
                dir.mkdirs();
                File outFile = new File(filePath);
                outFile.delete();
                Files.copy(file.getInputStream(), outFile.toPath());
            } catch (Exception e1) {
                e1.printStackTrace();
                continue;
            }

        }
        message.deleteCharAt(message.length() - 1);
        message.append(" export succeed");
        //显示对话框
        Messages.showMessageDialog(project, message.toString(), title, Messages.getInformationIcon());
    }
}
