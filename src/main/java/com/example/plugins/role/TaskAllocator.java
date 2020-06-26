package com.example.plugins.role;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.plugin.webfragment.contextproviders.AbstractJiraContextProvider;
import com.atlassian.jira.plugin.webfragment.model.JiraHelper;
import com.atlassian.jira.user.ApplicationUser;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class TaskAllocator extends AbstractJiraContextProvider {

    @Override
    public Map<String, Object> getContextMap(ApplicationUser applicationUser, JiraHelper jiraHelper) {
        Map<String, Object> contextMap = new HashMap<>();
        Issue currentIssue = (Issue) jiraHelper.getContextParams().get("issue");
        File File = null;

        String inputText = currentIssue.getSummary();

        InputStream input = com.atlassian.core.util.ClassLoaderUtils.getResourceAsStream("TestPython.py", this.getClass());
        try {
            File = File.createTempFile("tempfile", ".py");
        } catch (IOException e) {
            e.printStackTrace();
        }
        OutputStream out = null;
        try {
            out = new FileOutputStream(File);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int read = 0;
        byte[] bytes = new byte[1024];

        while (true) {
            try {
                if (!((read = input.read(bytes)) != -1)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.write(bytes, 0, read);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Process p = null;
        try {
            p = Runtime.getRuntime().exec("python " + File + " " + "\"" + inputText + "\"");
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String ret = null;
        try {
            ret = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        contextMap.put("label", ret);

        return contextMap;
    }
}
