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
                    response.sendError(404, "无效的操作类型");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            AjaxUtils.sendJsonMsg("操作失败：" + e.getMessage(), response);
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
        AjaxUtils.sendJsonMsg(wrapper, response);
    }

    // 帖子详情
    private void findPostDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = Long.parseLong(request.getParameter("id"));
        PostService service = new PostService();
        BbsPost post = service.findPostById(id);
        AjaxUtils.sendJsonMsg(post, response);
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
        AjaxUtils.sendJsonMsg(result > 0 ? "发帖成功" : "发帖失败", response);
    }

    // 更新帖子
    private void updatePost(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = parsePost(request.getReader());
        Long id = Long.valueOf(map.get("id").toString());

        PostService service = new PostService();
        BbsPost post = service.findPostById(id);
        if (post == null) {
            AjaxUtils.sendJsonMsg("帖子不存在", response);
            return;
        }
        post.setTitle((String) map.get("title"));
        post.setContent((String) map.get("content"));
        post.setBoardId(Long.valueOf(map.get("boardId").toString()));

        int result = service.updatePost(post);
        AjaxUtils.sendJsonMsg(result > 0 ? "更新成功" : "更新失败", response);
    }

    // 删除帖子
    private void deletePost(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = Long.parseLong(request.getParameter("id"));
        PostService service = new PostService();
        int result = service.deletePost(id);
        AjaxUtils.sendJsonMsg(result > 0 ? "删除成功" : "删除失败", response);
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