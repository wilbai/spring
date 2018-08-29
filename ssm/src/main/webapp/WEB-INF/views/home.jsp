<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Movie</title>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
</head>
<body>
    <div class="container" id="app">
        <router-view></router-view>
    </div>

    <script src="/static/js/vue.js"></script>
    <script src="/static/js/vue-resource.min.js"></script>
    <script src="/static/js/vue-router.min.js"></script>
    <script id="tableTemplate" type="vue/template">
        <a v-link="{path:'/new'}" class="btn btn-success">添加影片</a>
        <table class="table">
            <thead>
            <tr>
                <th>影片名称</th>
                <th width="50">评分</th>
                <th width="150">导演</th>
                <th width="100">发行年份</th>
                <th width="100">上映时间</th>
            </tr>
            </thead>
            <tbody>
                <template v-for="movie in movies">
                    <tr>
                        <td><a href="'/movie/'+{{movie.id}}">{{movie.title}}</a></td>
                        <td>{{movie.rate}}</td>
                        <td>{{movie.director}}</td>
                        <td>{{movie.releaseyear}}</td>
                        <td>{{movie.ontime}}</td>
                    </tr>
                </template>
            </tbody>
        </table>
    </script>

    <script type="vue/template" id="movieTemplate">
        <h3>{{movie.title}} <small>{{movie.rate}}</small></h3>
        <hr/>
        发行年份：{{movie.releaseyear}} 上映时间：{{movie.ontime}} 导演：{{movie.director}}
        <hr/>
        <p>{{movie.synopsis}}</p>
        <a @click="remove(movie)" class="btn btn-danger">删除</a>
        <button class="btn btn-info" @click="edit(movie)">编辑</button>
    </script>

    <script type="vue/template" id="newTemplate">
        <form>
            <legend>添加新影片</legend>
            <label>影片名称</label>
            <input type="text" v-model="movie.title">
            <label>评分</label>
            <input type="text" v-model="movie.rate">
            <label>导演</label>
            <input type="text" v-model="movie.director">
            <label>发行年份</label>
            <input type="text" v-model="movie.releaseyear">
            <label>上映日期</label>
            <input type="text" v-model="movie.ontime">
            <label>简介</label>
            <textarea type="text" v-model="movie.synopsis" style="width: 90%;height: 100px"></textarea>
            <div class="form-actions">
                <button class="btn btn-primary" type="button" @click="save()">保存</button>
            </div>
        </form>
    </script>
    <script type="vue/template" id="editTemplate">
        <form>
            <legend>编辑影片</legend>
            <label>影片名称</label>
            <input type="text" v-model="movie.title">
            <label>评分</label>
            <input type="text" v-model="movie.rate">
            <label>导演</label>
            <input type="text" v-model="movie.director">
            <label>发行年份</label>
            <input type="text" v-model="movie.releaseyear">
            <label>上映日期</label>
            <input type="text" v-model="movie.ontime">
            <label>简介</label>
            <textarea type="text" v-model="movie.synopsis" style="width: 90%;height: 100px"></textarea>
            <div class="form-actions">
                <button class="btn btn-primary" type="button" @click="save()">保存</button>
            </div>
        </form>
    </script>
    <script src="/static/js/app.js"></script>
</body>
</html>
