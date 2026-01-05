package com.example.ordermanager.servlet;

import com.alibaba.fastjson.JSON;
import com.example.ordermanager.entity.DataWrapper;
import com.example.ordermanager.entity.BbsPost;
import com.example.ordermanager.service.PostService;
import com.example.ordermanager.utils.AjaxUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/post")
public class PostServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // 统一返回结果封装
    private static class Result<T> {
        private int code; // 200成功，500失败
        private String msg;
        private T data;

        public Result(int code, String msg, T data) {
            this.code = code;
            this.msg = msg;
            this.data = data;
        }

        // getter/setter
        public int getCode() { return code; }
        public void setCode(int code) { this.code = code; }
        public String getMsg() { return msg; }
        public void setMsg(String msg) { this.msg = msg; }
        public T getData() { return data; }
        public void setData(T data) { this.data = data; }
    }

    public PostServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String op = request.getParameter("op");
        if (op == null) {
            op = "find";
        }
        try {
            switch (op) {
                case "find":
                    findByCondition(request, response);
                    break;
                case "detail":
                    findPostDetail(request, response);
                    break;
                case "insert":
                    insertPost(request, response);
                    break;
                case "update":
                    updatePost(request, response);
                    break;
                case "delete":
                    deletePost(request, response);
                    break;
                default:
                    AjaxUtils.sendJsonMsg(new Result<>(404, "无效的操作类型", null), response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            AjaxUtils.sendJsonMsg(new Result<>(500, "操作失败：" + e.getMessage(), null), response);
        }
    }

    // 分页+条件查询帖子
    private void findByCondition(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            System.out.println("page 解析失败，默认1");
        }
        int rows = 10;
        try {
            rows = Integer.parseInt(request.getParameter("rows"));
        } catch (Exception e) {
            System.out.println("rows 解析失败，默认10");
        }
        Long boardId = null;
        try {
            boardId = Long.parseLong(request.getParameter("boardId"));
        } catch (Exception e) {
            System.out.println("boardId 解析失败，默认null");
        }
        String title = request.getParameter("title") == null ? "" : request.getParameter("title");

        PostService service = new PostService();
        DataWrapper wrapper = service.find(page, rows, boardId, title);
        AjaxUtils.sendJsonMsg(new Result<>(200, "查询成功", wrapper), response);
    }

    // 帖子详情
    private void findPostDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = Long.parseLong(request.getParameter("id"));
        PostService service = new PostService();
        BbsPost post = service.findPostById(id);
        if (post == null) {
            AjaxUtils.sendJsonMsg(new Result<>(404, "帖子不存在", null), response);
            return;
        }
        AjaxUtils.sendJsonMsg(new Result<>(200, "查询成功", post), response);
    }

    // 新增帖子
    private void insertPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = parsePost(request.getReader());
        BbsPost post = new BbsPost();
        post.setTitle((String) map.get("title"));
        post.setContent((String) map.get("content"));
        post.setUserId(Long.valueOf(map.get("userId").toString()));
        post.setBoardId(Long.valueOf(map.get("boardId").toString()));

        PostService service = new PostService();
        int result = service.insertPost(post);
        if (result > 0) {
            AjaxUtils.sendJsonMsg(new Result<>(200, "发帖成功", post.getId()), response);
        } else {
            AjaxUtils.sendJsonMsg(new Result<>(500, "发帖失败", null), response);
        }
    }

    // 更新帖子
    private void updatePost(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = parsePost(request.getReader());
        Long id = Long.valueOf(map.get("id").toString());

        PostService service = new PostService();
        BbsPost post = service.findPostById(id);
        if (post == null) {
            AjaxUtils.sendJsonMsg(new Result<>(404, "帖子不存在", null), response);
            return;
        }
        post.setTitle((String) map.get("title"));
        post.setContent((String) map.get("content"));
        post.setBoardId(Long.valueOf(map.get("boardId").toString()));

        int result = service.updatePost(post);
        if (result > 0) {
            AjaxUtils.sendJsonMsg(new Result<>(200, "更新成功", null), response);
        } else {
            AjaxUtils.sendJsonMsg(new Result<>(500, "更新失败", null), response);
        }
    }

    // 删除帖子
    private void deletePost(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = Long.parseLong(request.getParameter("id"));
        PostService service = new PostService();
        int result = service.deletePost(id);
        if (result > 0) {
            AjaxUtils.sendJsonMsg(new Result<>(200, "删除成功", null), response);
        } else {
            AjaxUtils.sendJsonMsg(new Result<>(500, "删除失败", null), response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    // 解析POST JSON参数
    private Map<String, Object> parsePost(BufferedReader reader) {
        Map<String, Object> params = new HashMap<>();
        try {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            if (sb.length() > 0) {
                params = JSON.parseObject(sb.toString(), Map.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return params;
    }
}