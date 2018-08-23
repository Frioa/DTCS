package com.example.dtcs.util;

import java.text.SimpleDateFormat;
import android.util.Log;

import com.example.dtcs.db.Chk;
import com.example.dtcs.db.Competition;
import com.example.dtcs.db.Equipment;
import com.example.dtcs.db.ExperimentUser;
import com.example.dtcs.db.File;
import com.example.dtcs.db.GsonName;
import com.example.dtcs.db.GsonSafe;
import com.example.dtcs.db.GsonUser;
import com.example.dtcs.db.Lesson;
import com.example.dtcs.db.SignLogin;
import com.example.dtcs.db.User;
import com.google.gson.Gson;

import org.json.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by q9163 on 05/12/2017.
 */

public class Utility {
    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
    /*解析和处理服务器返回的数据*/
    public static boolean handleUserResqponse(String response){
        if (response.length()>2){
            try {
                Log.d("TextUtils true", ""+response);
                JSONArray userJson = new JSONArray(response);
                JSONObject nameJosn = userJson.getJSONObject(0);
                User user = new User();
                user.setUser_id(nameJosn.getString("id"));
                user.setName(nameJosn.getString("name"));
                user.setRank(nameJosn.getInt("rank"));
                user.setGrade(nameJosn.getString("grade"));
                user.setDepart(nameJosn.getString("depart"));
                user.setMajornum(nameJosn.getString("majornum"));
                user.setClass_(nameJosn.getInt("class"));
                user.setPhone(nameJosn.getString("phone"));
                user.setBirth(nameJosn.getString("birth"));
                user.setEmail(nameJosn.getString("email"));
                user.setPassword(nameJosn.getString("password"));
                user.setStatus(nameJosn.getInt("status"));
                user.setRemark(nameJosn.getString("remark"));
                Log.d("userJson", "handleUserResqponse: "+user.toString());
                user.saveThrows();
              /*  if(user.saveThrows()){
                    Log.d("userSave()", "保存成功");
                }else {
                    Log.d("userSave()", "保存失败");
                }*/

                return true;
            } catch (JSONException e){
                e.printStackTrace();
            }

        }else {
            Log.d("TextUtils false", ""+response);

        }
        return false;
    }
    //更新个人数据
    public static  boolean updataUserResqponse(String responese){

        if (responese.length()>2){
            try {
                JSONArray userJson = new JSONArray(responese);
                JSONObject nameJosn = userJson.getJSONObject(0);
                User user = new User();
                user.setUser_id(nameJosn.getString("id"));
                user.setName(nameJosn.getString("name"));
                user.setRank(nameJosn.getInt("rank"));
                user.setGrade(nameJosn.getString("grade"));
                user.setDepart(nameJosn.getString("depart"));
                user.setMajornum(nameJosn.getString("majornum"));
                user.setClass_(nameJosn.getInt("class"));
                user.setPhone(nameJosn.getString("phone"));
                user.setBirth(nameJosn.getString("birth"));
                user.setEmail(nameJosn.getString("email"));
                user.setPassword(nameJosn.getString("password"));
                user.setStatus(nameJosn.getInt("status"));
                user.setRemark(nameJosn.getString("remark"));
                 user.updateAll("user_id = ?",user.getUser_id());
              /*  if(user.saveThrows()){
                    Log.d("userSave()", "保存成功");
                }else {
                    Log.d("userSave()", "保存失败");
                }*/
                return true;
            } catch (JSONException e){
                e.printStackTrace();
            }

        }else {
            Log.d("TextUtils false", ""+responese);

        }
        return false;
    }
    //下载生日信息
    public static String loadBirthday(String response){
        String Name = "";
        try {
            JSONArray userJson = new JSONArray(response);
            for (int i=0;i<userJson.length();i++){
                JSONObject nameJosn = userJson.getJSONObject(i);
                Name = Name +nameJosn.getString("name")+" ";
            }

        }catch (JSONException e){
            e.printStackTrace();
        }

        return Name;
    }
    //获得实验员数据
    public static void  ExperimentData(String response){
        try {
            JSONArray userJson = new JSONArray(response);
            for (int i=0;i<userJson.length();i++){
                JSONObject nameJosn = userJson.getJSONObject(i);
                ExperimentUser experimentUser = new ExperimentUser();
                        experimentUser.setUser_id(nameJosn.getString("id"));
                experimentUser.setName(nameJosn.getString("name"));
                experimentUser.setGrade(nameJosn.getInt("grade"));
                experimentUser.setRemark(nameJosn.getString("remark"));
                experimentUser.setRank(nameJosn.getInt("rank"));
                experimentUser.setMajornum(nameJosn.getString("majornum"));
                experimentUser.saveThrows();
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

    }
    //下载用户签到数据
    public static void loafSignData(String response) throws Exception{
        try {
            JSONArray userJson = new JSONArray(response);
            for (int i=0;i<userJson.length();i++){
                JSONObject nameJosn = userJson.getJSONObject(i);
                SignLogin signLogin = new SignLogin();
                signLogin.setId(nameJosn.getString("id"));

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String date = nameJosn.getString("date");
                Date date1 =format.parse(date);
                Log.d("日期", "loafSignData: "+date1);
                signLogin.setDate(date1);

                signLogin.saveThrows();
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    //更新个人数据
    public static  void DownloadFileData(String responese) throws Exception {
        try {
            JSONArray userJson = new JSONArray(responese);
            for (int i = 0; i < userJson.length(); i++) {
                JSONObject nameJosn = userJson.getJSONObject(i);
                File file = new File();
                file.setFid(nameJosn.getInt("fid"));
                file.setPid(nameJosn.getString("pid"));
                file.setRid(nameJosn.getString("rid"));
                file.setFname(nameJosn.getString("fname"));
                file.setFtype(nameJosn.getString("ftype"));
                file.setSize(nameJosn.getDouble("size"));

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String date = nameJosn.getString("fdate");
                Date date1 = format.parse(date);
                Log.d("日期", "loafSignData: " + date1);
                file.setFdate(date1);

                file.setFdescript(nameJosn.getString("fdescript"));
                file.setFstatus(nameJosn.getString("fstatus"));
                file.setRemark(nameJosn.getString("remark"));
                file.saveThrows();
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

    }

    //设备信息
    public static void DownEquipInfo(String responese) {
        try {
            JSONArray userJson = new JSONArray(responese);
            for (int i = 0; i < userJson.length(); i++) {
                JSONObject nameJosn = userJson.getJSONObject(i);
                Equipment eq = new Equipment();
                eq.setE_id(nameJosn.getString("id"));
                eq.setName(nameJosn.getString("name"));
                eq.setVersion(nameJosn.getString("verison"));
                eq.setType(nameJosn.getInt("type"));
                eq.setCount(nameJosn.getInt("count"));
                eq.setSymbol(nameJosn.getInt("symbol"));

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String date = nameJosn.getString("checkdate");
                Date date1 = format.parse(date);
                Log.d("日期", "loafSignData: " + date1);
                eq.setCheckdate(date1);

                eq.setStatus(nameJosn.getInt("status"));
                eq.setRemark(nameJosn.getString("remark"));

                eq.saveThrows();
            }
        }catch(JSONException e){
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //安全检查信息
    public static void SafeCheckInfo(String responese) {
        try {
            JSONArray userJson = new JSONArray(responese);
            for (int i = 0; i < userJson.length(); i++) {
                JSONObject nameJosn = userJson.getJSONObject(i);
                Chk chk = new Chk();
                chk.setChka(nameJosn.getInt("chka"));
                chk.setChkb(nameJosn.getInt("chkb"));
                chk.setChkc(nameJosn.getInt("chkc"));
                chk.setChkd(nameJosn.getInt("chkd"));
                chk.setChke(nameJosn.getInt("chke"));
                chk.setChkf(nameJosn.getInt("chkf"));
                chk.setChkg(nameJosn.getInt("chkg"));

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String date = nameJosn.getString("cdate");
                Date date1 = format.parse(date);
                Log.d("日期", "loafSignData: " + date1);
                chk.setCdate(date1);

                chk.setName(nameJosn.getString("name"));

                chk.saveThrows();
            }
        }catch(JSONException e){
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //名字转ID
    public static String NameTrunID(String responese) {
        try {
            JSONArray userJson = new JSONArray(responese);

            JSONObject nameJosn = userJson.getJSONObject(0);
            Log.d("名字转ID", "NameTrunID: "+nameJosn.getString("id"));
            return nameJosn.getString("id");
        }catch(JSONException e){
            e.printStackTrace();
        }
        return  "";
    }

    //课程检查信息
    public static void LessonsInfo(String responese) {
        try {
            JSONArray userJson = new JSONArray(responese);
            for (int i = 0; i < userJson.length(); i++) {
                JSONObject nameJosn = userJson.getJSONObject(i);
                Lesson lesson = new Lesson();
                lesson.setLessonID(nameJosn.getString("id"));
                lesson.setName(nameJosn.getString("name"));
                lesson.setLongs(nameJosn.getInt("longs"));

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String date = nameJosn.getString("sdate");
                Date date1 = format.parse(date);
                Log.d("日期", "loafSignData: " + date1);
                lesson.setSdata(date1);

                lesson.setType(nameJosn.getInt("type"));
                lesson.setStatus(nameJosn.getInt("status"));
                lesson.setTaech(nameJosn.getString("teach"));
                lesson.setPoint(nameJosn.getInt("point"));
                lesson.setRemark(nameJosn.getString("remark"));

                lesson.saveThrows();
            }
        }catch(JSONException e){
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //比赛信息
    public static void CompetInfo(String responese) {
        try {
            JSONArray userJson = new JSONArray(responese);
            for (int i = 0; i < userJson.length(); i++) {
                JSONObject nameJosn = userJson.getJSONObject(i);
                Competition compet = new Competition();
                compet.setUserid(nameJosn.getString("id"));
                compet.setName(nameJosn.getString("name"));
                compet.setDepatment(nameJosn.getString("department"));
                compet.setMajor(nameJosn.getString("major"));
                compet.setClass_(nameJosn.getInt("class"));
                compet.setGrade(nameJosn.getDouble("grade"));
                compet.setSymbol(nameJosn.getInt("symbol"));
                compet.setRemark(nameJosn.getString("remark"));
                compet.saveThrows();
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    //比赛信息
    public static String JinShan(String responese) {
        String Jinshan = "";

        try {
            JSONObject userJson = new JSONObject(responese);
            Jinshan = userJson.getString("note");
        }catch(JSONException e){
            e.printStackTrace();
        }
        return Jinshan;
    }

    /**
     *上传数据
     */
    //提交用户签到信息
    public static String UserSignResponse(String Url,String userID){
        String result = "defeat";
        //申明给服务端传递一个json串
        //创建一个OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
        Gson gson = new Gson();
        GsonName json =new GsonName();
        json.setUesrName(userID);

        String JsonUserID = gson.toJson(json);
        Log.d("签到的用户id:",JsonUserID);
        RequestBody requestBody = RequestBody.create(JSON,JsonUserID);

        Request request = new Request.Builder()
                .url(Url)
                .post(requestBody)
                .build();
        //发送请求获取响应
        try {
            Response response=okHttpClient.newCall(request).execute();
            //判断请求是否成功
            if(response.isSuccessful()){
                //打印服务端返回结果
                result =  response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("服务端返回结果:",result);
        return result;
    }

    //提交安全信息
    public static String UploadSafeResponse(String Url,GsonSafe safe){
        String result = "defeat";
        //申明给服务端传递一个json串
        //创建一个OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
        Gson gson = new Gson();
        String JsonUserID = gson.toJson(safe);
        Log.d("安全信息:",JsonUserID);
        RequestBody requestBody = RequestBody.create(JSON,JsonUserID);

        Request request = new Request.Builder()
                .url(Url)
                .post(requestBody)
                .build();
        //发送请求获取响应
        try {
            Response response=okHttpClient.newCall(request).execute();
            //判断请求是否成功
            if(response.isSuccessful()){
                //打印服务端返回结果
                result =  response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("服务端返回结果:",result);
        return result;
    }

   /* //打开网页端
    public static String DTCSResponse(String Url,GsonUser user){
        String result = "defeat";
        //申明给服务端传递一个json串
        //创建一个OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
        Gson gson = new Gson();
        String JsonUserID = gson.toJson(user);
        Log.d("用户信息:",JsonUserID);
        RequestBody requestBody = RequestBody.create(JSON,JsonUserID);

        Request request = new Request.Builder()
                .url(Url)
                .post(requestBody)
                .build();
        //发送请求获取响应
        try {
            Response response=okHttpClient.newCall(request).execute();
            //判断请求是否成功
            if(response.isSuccessful()){
                //打印服务端返回结果
                result =  response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("服务端返回结果:",result);
        return result;
    }*/
}
