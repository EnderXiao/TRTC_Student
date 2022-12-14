package com.example.trtc_client;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.trtc_client.adapter.MyAdapter;
import com.example.trtc_client.utils.MyImageGetter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class AnswerQuestionFragment extends Fragment implements View.OnClickListener{
    final Context context = getActivity();
    private View FcontentView; //Fragment???????????????view

    private double screenWidth , screenHeight;

    private Chronometer jishiqi;
    private TextView tx_answers_sum; //????????????/?????????

    private TextView txType_tiwen; //????????????-??????
    private TextView txType_suiji; //????????????-??????
    private TextView txType_qiangda; //????????????-??????
    private ImageView img_tiwen , img_suiji , img_qiangda;
    private int txType = 1;  ///1??????2??????3??????


    private TextView txModle_danxuan; //????????????-??????
    private TextView txModle_duoxuan; //????????????-??????
    private TextView txModle_panduan; //????????????-??????
    private TextView txModle_luru; //????????????-??????

    private ImageView imgdanxuan , imgduoxuan , imgpanduan , imgluru;

    private LinearLayout linear_choose; //txChoose+tx_choosenum+img_choose
    private TextView txChoose  , tx_choosenum; //?????????
    private ImageView img_choose;
//    private Spinner spinner; //???????????????
//    private TextView spinner;
    private int chooseNum = 4; //?????????????????????4
    private PopupWindow pw_chooseNum;
    private TextView tx_2 , tx_3 , tx_4 , tx_5 , tx_6 , tx_7 , tx_8 ;

    private Button btBegin1 , btBegin2; //??????????????????????????????????????????
    private LinearLayout linear1 , linear2; //????????????????????? ????????????  ???????????? ????????????;???????????????
    private Button btSingle , btAnswers , btEnd , btClosed; //???????????? ????????????  ???????????? ????????????

    private BarChart barChart; //?????????
    private TextView tx_noanswer; //?????????????????????
    private ScrollView slStusAnswers; //????????????

    //?????????
    private ScrollView slStusAnswersImg; //????????????
    private ScrollView slStusAnswers_zhuguan;  //????????????
    private TextView tx_noanswer_zhuguan;//?????????????????????

    //??????UI
    private LinearLayout linear_danxuan;
    private ImageView a , b , c , d , e , f , g , h;

    //??????UI
    private LinearLayout linear_duoxuan;
    private ImageView a1 , b1 , c1 , d1 , e1 , f1 , g1 , h1;

    //??????UI
    private LinearLayout linear_panduan;
    private ImageView right , error;
    private String answer;
    private boolean setAnswer_status; //????????????????????????

    //??????UI(??????????????????
    private LinearLayout linear_danxuan_sq;
    private ImageView a_sq , b_sq , c_sq , d_sq , e_sq , f_sq , g_sq , h_sq;

    //??????UI(??????????????????
    private LinearLayout linear_duoxuan_sq;
    private ImageView a1_sq , b1_sq , c1_sq , d1_sq , e1_sq , f1_sq , g1_sq , h1_sq;

    //??????UI(??????????????????
    private LinearLayout linear_panduan_sq;
    private ImageView right_sq , error_sq;
    private String answer_sq;
    private boolean setAnswer_status_sq; //????????????????????????

    //????????????????????????
    /**
     * 0:?????????
     * 1:????????????????????????????????????
     * 2:?????????????????????=>?????????????????????????????????
     * 3:?????????????????????
     */
    private int suiji_qiangda_flag = 0;

    //????????????????????????????????????id????????????
    private String stuName_selected , stuId_selected , stuAnswer_selected;
    //??????(??????/?????? ??????????????????????????????)
    private Timer mTimer;
    //??????(?????? ???????????????????????????????????????)
    private Timer mTimer_stuNum;

    private PopupWindow pw_selectStu;
    private ImageView img_zan;
    private TextView tx_stuname , tx_tishi;
    private boolean isClick_btClose = false;
    //??????/?????? ????????????????????????????????????
    private PopupWindow pw_selectStuAnswer , pw_setAnswer;


    //???????????????????????????????????????
    private int selectedIndex = 0;
    private TextView tx_huizong;
    private boolean isSelect_huizong = true;
    private MyAdapter myAdapter;

    //???????????? ???????????????????????????????????????????????????????????????????????????
    private TextView tx_quick , tx_top1 , tx_top2 , tx_top3;
    private ImageView img_top1 , img_top2 , img_top3;

    private TextView tx_answer1 , tx_answer2 , tx_right1 , tx_right2 , tx_dati1 , tx_dati2;
    private LinearLayout linear_right , linear_quick , linear_answer;

    //??????????????????????????????????????????????????????
    private List<String> listAnswers;
    private List<List<String>> listStusName;

    //?????????????????????
    private List<String> stusNameList;
    private List<String> stusAnswerList;
    private int selectStuAnswerIndex = 0; //???????????????????????????????????????stusNameList???

    //??????????????????????????????
    private List<List<String>> stusAnswerList_name_txt;
    private List<String> stusAnswerList_answer_txt;

    //??????????????????????????????
    private List<String> stusAnswerList_name_img;
    private List<String> stusAnswerList_answer_img;

    //??????????????????(?????????)
    private List<String> stusAnswerList_Noanswer = Arrays.asList(new String[]{
            "?????????" , "?????????" , "?????????" , "??????" , "?????????" , "?????????"
    });

    public void setListitem(List<String> listitem) {
        this.listitem.clear();
        this.listitem.addAll(listitem);
        myAdapter.notifyDataSetChanged();

    }

    private  List<String> listitem;

    public AnswerQuestionFragment() {
        // Required empty public constructor
        initJoinClassInformation();
    }
    //??????????????????
    private void getScreenProps(){
        //???????????????????????????????????????????????????????????????????????????????????????????????????
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
        Log.e("", "width: " + screenWidth + ",height:" + screenHeight);
    }

    //??????32???uuid??????questionId????????????32??????
    private void getUUID(){
        System.out.println("??????answerQuestionId???: " + AnswerActivityTea.answerQuestionId);
        AnswerActivityTea.answerQuestionId = UUID.randomUUID().toString();
        System.out.println("??????answerQuestionId???: " + AnswerActivityTea.answerQuestionId);
    }

    //UI??????????????????
    private void bindViews() {
        txType_tiwen.setOnClickListener(this);
        txType_suiji.setOnClickListener(this);
        txType_qiangda.setOnClickListener(this);
        txModle_danxuan.setOnClickListener(this);
        txModle_duoxuan.setOnClickListener(this);
        txModle_panduan.setOnClickListener(this);
        txModle_luru.setOnClickListener(this);

        img_tiwen.setOnClickListener(this);
        img_suiji.setOnClickListener(this);
        img_qiangda.setOnClickListener(this);

        imgdanxuan.setOnClickListener(this);
        imgduoxuan.setOnClickListener(this);
        imgpanduan.setOnClickListener(this);
        imgluru.setOnClickListener(this);
    }

    //?????????????????????????????????(????????????)
    private void setSelected1(){
        txType_tiwen.setSelected(false);
        txType_suiji.setSelected(false);
        txType_qiangda.setSelected(false);

        img_tiwen.setSelected(false);
        img_suiji.setSelected(false);
        img_qiangda.setSelected(false);
    }
    //?????????????????????????????????(????????????)
    private void setSelected2(){
        txModle_danxuan.setSelected(false);
        txModle_duoxuan.setSelected(false);
        txModle_panduan.setSelected(false);
        txModle_luru.setSelected(false);

        imgdanxuan.setSelected(false);
        imgduoxuan.setSelected(false);
        imgpanduan.setSelected(false);
        imgluru.setSelected(false);
    }

    //???????????????????????????????????????
    private boolean isSelected(){
        if(txModle_danxuan.isSelected()
                || txModle_duoxuan.isSelected()
                || txModle_panduan.isSelected()
                || txModle_luru.isSelected()
        ){
            return true;
        }
        return false;
    }

    //??????????????????(????????????
    private void showStusAnswers(View v){
        System.out.println("??????????????????????????????????????????????????????????????????????????????????????????????????????");
        System.out.println("????????????: " + AnswerActivityTea.answerStu);
        LinearLayout linearStusAnswers = v.findViewById(R.id.linearStusAnswers);
        //????????????
        linearStusAnswers.removeAllViews();
        splitStuAnswers(); //??????????????????
        //?????????????????????
        int linearSum = listAnswers.size();
        LinearLayout[] answersList = new LinearLayout[linearSum];
        for(int k = 0 ; k < answersList.length ; k++){
            answersList[k] = new LinearLayout(getActivity());
            //????????????
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            answersList[k].setLayoutParams(params);
            answersList[k].setOrientation(LinearLayout.HORIZONTAL);
        }
        boolean flag = false; //??????????????????????????????????????????????????????
        for(int i = 0 ; i < listStusName.size() ; i++){
            if(listAnswers.get(i).equals(answer)){
                flag = true;
            }

            TextView txt_answerTitle = new TextView(getActivity());
            //????????????
            LinearLayout.LayoutParams tparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            tparams.setMargins(30 , 3 , 0 , 0);
            txt_answerTitle.setLayoutParams(tparams);
//            txt_answerTitle.setTextSize(15);
            txt_answerTitle.setTextColor(Color.parseColor("#828798"));

            TextView txt_answer = new TextView(getActivity());
            //????????????
            LinearLayout.LayoutParams txt_params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            txt_params.setMargins(5 , 3 , 0 , 0);
            txt_answer.setLayoutParams(txt_params);
//            txt_answer.setTextSize(20);
            if(listAnswers.get(i).equals("??????")){
                txt_answerTitle.setText("?????????");
                answersList[i].addView(txt_answerTitle);
            }else{
                txt_answerTitle.setText("??????: ");
                txt_answer.setTextColor(Color.parseColor("#90d7ec"));
                txt_answer.setText(listAnswers.get(i));
                answersList[i].addView(txt_answerTitle);
                answersList[i].addView(txt_answer);
            }
            linearStusAnswers.addView(answersList[i]);

            //???????????????????????????????????????????????????
            LinearLayout linearAll = new LinearLayout(getActivity());
            //????????????
            LinearLayout.LayoutParams linear_params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            linear_params.setMargins(20 , 0 , 20 , 0 );
            linearAll.setLayoutParams(linear_params);
            linearAll.setOrientation(LinearLayout.VERTICAL);

            int linearCount = (int)Math.ceil(listStusName.get(i).size() / 8.0);
            Log.e("?????????????????????:   " , listStusName.get(i).size() + "");
            Log.e("?????????????????????linear:   " , linearCount + "");
            LinearLayout[] linearList = new LinearLayout[linearCount];
            for(int k = 0 ; k < linearList.length ; k++){
                linearList[k] = new LinearLayout(getActivity());
                //????????????
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                linearList[k].setLayoutParams(params);
                linearList[k].setWeightSum(8);
                linearList[k].setOrientation(LinearLayout.HORIZONTAL);
            }
            for(int j = 0 ; j < listStusName.get(i).size() ; j++){
                TextView txt_name = new TextView(getActivity());
                //????????????
//                LinearLayout.LayoutParams txtname_params = new LinearLayout.LayoutParams(70, 30);
                LinearLayout.LayoutParams txtname_params = new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1);

                //????????????8???????????????
                if(j % 8 == 0){
                    txtname_params.setMargins(10 , 3 , 5 , 3);
                }else{
                    txtname_params.setMargins(5 , 3 , 5 , 3);
                }
                txt_name.setLayoutParams(txtname_params);
                txt_name.setPadding(5 , 2 , 5 , 2);
//                txt_name.setTextSize(15);
                txt_name.setGravity(Gravity.CENTER);
                txt_name.setText(listStusName.get(i).get(j));
//                txt_name.setBackgroundColor(Color.parseColor("#007947"));
                if(flag){ //????????????
                    txt_name.setBackgroundColor(Color.parseColor("#84bf96"));
                    txt_name.setTextColor(Color.parseColor("#007947"));
                }else{
                    txt_name.setBackgroundColor(Color.parseColor("#d3d7d4"));
                    txt_name.setTextColor(Color.parseColor("#828798"));
                }
                linearList[j / 8].addView(txt_name);
            }
            flag = false;
            for(int k = 0 ; k < linearList.length ; k++){
                linearAll.addView(linearList[k]);
            }
            linearStusAnswers.addView(linearAll);
        }
    }

    //??????????????????-?????????
    private void splitStuAnswers(){
        System.out.println("??????????????????????????????????????????????????????????????????");
        //AnswerActivityTea.answerStu??????:
        // D:??????_2,??????_5,?????????,@#@A:??????_3,@#@B:??????_1,?????????,?????????,@#@C:??????_0,??????_4,?????????,@#@
        String stuAnswers = AnswerActivityTea.answerStu;
        System.out.println("????????????1111:" + stuAnswers);
        String[] answer_names_list = stuAnswers.split(",@#@");
        for(int i = 0 ; i < answer_names_list.length ; i++){
            System.out.println("????????????2222:" + answer_names_list[i]);
        }

        //????????????
        if(listAnswers != null){
            listAnswers.clear();
        }else{
            listAnswers = new ArrayList<>();
        }
        //??????????????????
        if(listStusName != null){
            listStusName.clear();
        }else{
            listStusName = new ArrayList<List<String>>();
        }
        for(int i = 0 ; i < answer_names_list.length ; i++){
            String answer = answer_names_list[i].substring(0 , answer_names_list[i].indexOf(":"));
            listAnswers.add(answer);

            String names = answer_names_list[i].substring(answer_names_list[i].indexOf(":") + 1);
            String[] name_list = names.split(",");
            List<String> name_List = new ArrayList<String>(Arrays.asList(name_list));
            listStusName.add(name_List);
        }
        System.out.println("listAnswers: " + listAnswers);
        System.out.println("listStusName: " + listStusName);
    }

    //??????????????????
    private int getAnswerCurrentStuNum(){
        int count = 0;
        for(int i = 0 ; i < listAnswers.size() ; i++){
            if(listAnswers.get(i).equals(answer)){
                return listStusName.get(i).size();
            }
        }
        return count;
    }

    //????????????????????????(????????????)
    private List<String> getAnswerCurrentStuNames(){
        List<String> stus_right = new ArrayList<>();
        for(int i = 0 ; i < listAnswers.size() ; i++){
            if(listAnswers.get(i).equals(answer)){
                stus_right.addAll(listStusName.get(i));
                break;
            }
        }
        return stus_right;
    }

    //?????????????????????????????????StuAnswerBean????????????3???)
    private List<StuAnswerBean> getACStuBeanList(int num){
        int findNum = num >= 3 ? 3 : num;
        List<StuAnswerBean> answerRight_stuslistTop = new ArrayList<StuAnswerBean>();
        for(int i = 0; i < AnswerActivityTea.alist.size() ; i++){
            if(answerRight_stuslistTop.size() >= findNum){
                break;
            }else{
                if(AnswerActivityTea.alist.get(i).stuAnswer.equals(answer)){
                    answerRight_stuslistTop.add(AnswerActivityTea.alist.get(i));
                }
            }
        }
        return answerRight_stuslistTop;
    }


    //????????????????????????????????????????????????
    private void isShowMoreInformation(int flag , int index){
        System.out.println("???????????????????????????????????????????????????????????????!!!!!!!!!!!!!");
        if(answer != null && answer.length() > 0){ //????????????????????? flag=1????????????????????????
            splitStuAnswers(); //??????????????????
//            List<String> answerRight_stuList = getAnswerCurrentStuNames(); //??????????????????
            int answer_rightNum = getAnswerCurrentStuNum(); //????????????
            System.out.println("????????????: " + answer_rightNum);
            if(flag == 1){ //????????????????????? flag=1????????????????????????
                if(answer_rightNum > 0){ //?????????????????????????????????
                    linear_quick.setVisibility(View.VISIBLE);
                    img_top1.setVisibility(View.INVISIBLE);
                    tx_top1.setVisibility(View.INVISIBLE);
                    img_top2.setVisibility(View.INVISIBLE);
                    tx_top2.setVisibility(View.INVISIBLE);
                    img_top3.setVisibility(View.INVISIBLE);
                    tx_top3.setVisibility(View.INVISIBLE);
                    //???????????????????????????(??????3??????
                    List<StuAnswerBean> answerRight_stuslistTop = getACStuBeanList(answer_rightNum);
                    for(int i = 0 ; i < answerRight_stuslistTop.size() ; i++){
                        System.out.println("??????????????????: " + (i + 1) + " , " + answerRight_stuslistTop.get(i).name);
                    }
                    int quickNum = answerRight_stuslistTop.size();
                    if(quickNum >= 1){
                        img_top1.setVisibility(View.VISIBLE);
                        tx_top1.setVisibility(View.VISIBLE);
                        tx_top1.setText(answerRight_stuslistTop.get(0).name);
                    }
                    if(quickNum >= 2){
                        img_top2.setVisibility(View.VISIBLE);
                        tx_top2.setVisibility(View.VISIBLE);
                        tx_top2.setText(answerRight_stuslistTop.get(1).name);
                    }
                    if(quickNum >= 3){
                        img_top3.setVisibility(View.VISIBLE);
                        tx_top3.setVisibility(View.VISIBLE);
                        tx_top3.setText(answerRight_stuslistTop.get(2).name);
                    }
                }else{
                    linear_quick.setVisibility(View.GONE);
                }
            }else{
                linear_quick.setVisibility(View.GONE);
            }
            //???????????????
            int classAllNum = AnswerActivityTea.alist.size(); //?????????????????????
//            if(index == -1){ //??????????????????
//                classAllNum = getJoinClassAllStuNum();
//            }else{ //??????????????????????????????
//                classAllNum = AnswerActivityTea.classList.get(index).stuNum;
//            }
            System.out.println("???????????????: " + classAllNum);
            linear_right.setVisibility(View.VISIBLE);
            int right_per = (int)(answer_rightNum * 1.0 / classAllNum * 100);
            System.out.println("???????????????: " + classAllNum + " , ????????????: " + answer_rightNum + " , ?????????: " + right_per + "%");
            tx_right2.setText(right_per + "%");
            linear_answer.setVisibility(View.VISIBLE);
            tx_answer2.setText(answer);
        }else{ //???????????????
//            if(flag != 1){ //??????????????????
                linear_quick.setVisibility(View.GONE);
                linear_answer.setVisibility(View.GONE);
                linear_right.setVisibility(View.INVISIBLE);
//            }
        }
    }

    //??????????????????????????????
    private int getJoinClassAllStuNum(){
        int count = 0 ;
        for(int i = 0; i < AnswerActivityTea.classList.size() ; i++){
            count += AnswerActivityTea.classList.get(i).stuNum;
        }
        System.out.println("??????????????????????????????:   " + count);
        return count;
    }

    //??????????????????????????????ming???gege?????????????????????
    private void initJoinClassInformation(){
        //?????????AnswerActivity.ketangList   AnswerActivityTea.joinList???????????????????????????
        if(AnswerActivityTea.classList != null){
            AnswerActivityTea.classList.clear();
        }
        if(AnswerActivityTea.classList == null){
            AnswerActivityTea.classList = new ArrayList<>();
        }
        if(AnswerActivityTea.ketangList != null && AnswerActivityTea.ketangList.size() > 0){
            for(int i = 0; i < AnswerActivityTea.ketangList.size() ; i++){
                KeTangBean classItem = new KeTangBean(
                        AnswerActivityTea.ketangList.get(i).getUserId() ,
                        AnswerActivityTea.ketangList.get(i).getName() ,
                        AnswerActivityTea.ketangList.get(i).getNum());
                AnswerActivityTea.classList.add(classItem);
            }
        }
        if(AnswerActivityTea.joinList != null && AnswerActivityTea.joinList.size() > 0){
            KeTangBean class_yidong = new KeTangBean("zb" , "???????????????" , AnswerActivityTea.joinList.size());
            AnswerActivityTea.classList.add(class_yidong);
        }
//        KeTangBean class_ming = new KeTangBean("4193" , "??????2022????????????" , 60);
//        KeTangBean class_ge = new KeTangBean("4195ketang" , "??????2022????????????" , 60);
//        KeTangBean class_yidong = new KeTangBean("zb" , "???????????????" , 20);
//        AnswerActivityTea.classList.add(class_ming);
//        AnswerActivityTea.classList.add(class_ge);
//        AnswerActivityTea.classList.add(class_yidong);
    }

    //????????????????????????
    private void initHttpData_memberAnswer(){
        AnswerActivityTea.ylist = null;
        AnswerActivityTea.xlist = null;
        AnswerActivityTea.alist = null;
        AnswerActivityTea.submitAnswerStatus = false;
        AnswerActivityTea.answer = "";
        AnswerActivityTea.answerStu = "";
    }


    //???http??????????????????????????????????????????????????????????????????????????????????????????
    private void updateHttpData_memberAnswer(JSONObject jsonObject) throws JSONException {
        //?????????-????????????????????????????????????????????????
        JSONArray array_ylist = jsonObject.getJSONArray("ylist");
        if(AnswerActivityTea.ylist != null){
            AnswerActivityTea.ylist.clear();
        }
        if(AnswerActivityTea.ylist == null){
            AnswerActivityTea.ylist = new ArrayList<>();
        }
        for(int i = 0 ; i < array_ylist.length() ; i++){
            AnswerActivityTea.ylist.add((Integer) array_ylist.get(i));
        }

        System.out.println("ylist==>" + AnswerActivityTea.ylist);
        //?????????-?????????????????????
        JSONArray array_xlist = jsonObject.getJSONArray("xlist");
        if(AnswerActivityTea.xlist != null){
            AnswerActivityTea.xlist.clear();
        }
        if(AnswerActivityTea.xlist == null){
            AnswerActivityTea.xlist = new ArrayList<>();
        }
        for(int i = 0 ; i < array_xlist.length() ; i++){
            AnswerActivityTea.xlist.add((String) array_xlist.get(i));
        }
        System.out.println("xlist==>" + AnswerActivityTea.xlist);
        //?????????????????????
        JSONArray array_alist = jsonObject.getJSONArray("alist");
        if(AnswerActivityTea.alist != null){
            AnswerActivityTea.alist.clear();
        }
        if(AnswerActivityTea.alist == null){
            AnswerActivityTea.alist = new ArrayList<>();
        }
        for(int i = 0 ; i < array_alist.length() ; i++){
            StuAnswerBean item = new StuAnswerBean();
            item.questionId = array_alist.getJSONObject(i).getString("questionId");
            item.name = array_alist.getJSONObject(i).getString("name");
            item.userId = array_alist.getJSONObject(i).getString("userId");
            item.stuAnswer = array_alist.getJSONObject(i).getString("stuAnswer");
            item.stuAnswerTime = array_alist.getJSONObject(i).getLong("stuAnswerTime");
            item.startAnswerTime = array_alist.getJSONObject(i).getLong("startAnswerTime");
            AnswerActivityTea.alist.add(item);
            System.out.println("item==>" + item.toString());
        }
        System.out.println("alist???????????????????????????==>" + AnswerActivityTea.alist.size());
        //??????????????????
        boolean status = jsonObject.getBoolean("status");
        AnswerActivityTea.submitAnswerStatus = status;
        System.out.println("????????????????????????==>" + AnswerActivityTea.submitAnswerStatus);
        //????????????
        String answer = jsonObject.getString("answer");
        AnswerActivityTea.answer = answer;
        System.out.println("???????????????==>" + AnswerActivityTea.answer);
        //??????????????????
        String answerStu = jsonObject.getString("answerStu");
        AnswerActivityTea.answerStu = answerStu;
        System.out.println("??????????????????==>" + AnswerActivityTea.answerStu);
    }

    //????????????????????????????????????????????????????????????  aflag:????????????-1???????????????-2 index:???????????????-1?????????????????????????????????index  v:???????????????popupwindow
    private void getJoinClassMemberSubmitAnswerInf(String ketangId , int stuNum , int index , int aflag , View v){
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
                initHttpData_memberAnswer();
//                int count = AnswerActivityTea.alist != null && AnswerActivityTea.alist.size() > 0 ? AnswerActivityTea.alist.size() : 0;
                System.out.println("??????????????????111:  " + AnswerActivityTea.xlist);
                JSONObject jsonObject = Http_HuDongActivityTea.getSubmitAnswerClass_keguan(ketangId , stuNum);
                String status = "";
                if(jsonObject != null){
                    try {
                        updateHttpData_memberAnswer(jsonObject);
                        status = jsonObject.getString("status");
                        System.out.println("??????????????????222:  " + status);
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
                if(jsonObject != null && status != null && status.length() > 0){
//                    count = AnswerActivityTea.alist != null && AnswerActivityTea.alist.size() > 0 ? AnswerActivityTea.alist.size() : 0;
                    System.out.println("??????????????????333:  " + AnswerActivityTea.xlist);
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            //???????????????
                            if(ketangId.equals("all")){
                                int all_count = getJoinClassAllStuNum();
                                int answer_count = 0;
                                for(int i = 0; i < AnswerActivityTea.ylist.size() ; i++){
                                    answer_count += AnswerActivityTea.ylist.get(i);
                                }
                                tx_dati2.setText((int)(answer_count * 1.0 / all_count * 100) + "%");
                                System.out.println("?????????:  " + all_count + " ,  ????????????:  " + answer_count + " ,  ?????????:  " + (int)(answer_count * 1.0 / all_count * 100));
                            }else{
                                int all_count = AnswerActivityTea.classList.get(index).stuNum;
                                int answer_count = 0;
                                for(int i = 0; i < AnswerActivityTea.ylist.size() ; i++){
                                    answer_count += AnswerActivityTea.ylist.get(i);
                                }
                                tx_dati2.setText((int)(answer_count * 1.0 / all_count * 100) + "%");
                                System.out.println("?????????:  " + all_count + " ,  ????????????:  " + answer_count + " ,  ?????????:  " + (int)(answer_count * 1.0 / all_count * 100));
                            }
                            if(aflag == 1){ //????????????
                                //????????????????????????????????????????????????????????????
                                barChart.setVisibility(View.INVISIBLE); //????????????????????????????????????????????????????????????????????????
                                barChart.setVisibility(View.VISIBLE);

                                tx_noanswer.setVisibility(View.GONE);

                                isShowMoreInformation(aflag , index);

                                setAxis(AnswerActivityTea.xlist); // ???????????????
                                setLegend(); // ????????????
                                setData(AnswerActivityTea.xlist, AnswerActivityTea.ylist);  // ????????????
                            }else{ //????????????
                                tx_noanswer.setVisibility(View.GONE);
                                isShowMoreInformation(aflag , index);
                                showStusAnswers(v); //????????????????????????
                            }
                        }
                    });
                }else{
//                    Toast.makeText(getActivity(), "?????????????????????" + answer, Toast.LENGTH_SHORT).show();
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            slStusAnswers.setVisibility(View.GONE);
                            barChart.setVisibility(View.GONE);
                            tx_noanswer.setVisibility(View.VISIBLE);
                            tx_dati2.setText("0%");
                            linear_quick.setVisibility(View.GONE);
                            if(answer != null && answer.length() > 0){
                                linear_answer.setVisibility(View.VISIBLE);
                                tx_answer2.setText(answer);
                            }else{
                                linear_answer.setVisibility(View.INVISIBLE);
                            }
                            linear_right.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }
        }).start();
    }

    //????????????(????????????????????????????????????)
    private void showPopupWindow(View v , int flag){
        initJoinClassInformation(); //???????????????????????????
        //???popupWindow???????????????????????????view??????popupWindow???
        if(screenWidth <= 0){
            getScreenProps();
        }
        PopupWindow popupWindow = new PopupWindow(v , (int)(screenWidth * 0.75) , (int)(screenHeight * 0.8) , false);

        //???????????????????????????Gravity.CENTER,x???y????????????Gravity.CENTER?????????
        popupWindow.showAtLocation(v , Gravity.CENTER , 0 , 0);

        //????????????????????????????????????
        tx_huizong = v.findViewById(R.id.tx_huizong);
        ListView lvClass = v.findViewById(R.id.lvClass);
        lvClass.setDivider(null); //???????????????

        //???????????? ???????????????????????????????????????????????????????????????????????????
        tx_quick = v.findViewById(R.id.tx_quick);
        img_top1 = v.findViewById(R.id.img_top1);
        tx_top1 = v.findViewById(R.id.tx_top1);
        img_top2 = v.findViewById(R.id.img_top2);
        tx_top2 = v.findViewById(R.id.tx_top2);
        img_top3 = v.findViewById(R.id.img_top3);
        tx_top3 = v.findViewById(R.id.tx_top3);

        tx_answer1 = v.findViewById(R.id.tx_answer1);
        tx_answer2 = v.findViewById(R.id.tx_answer2);
        tx_right1 = v.findViewById(R.id.tx_right1);
        tx_right2 = v.findViewById(R.id.tx_right2);
        tx_dati1 = v.findViewById(R.id.tx_dati1);
        tx_dati2 = v.findViewById(R.id.tx_dati2);

        linear_right = v.findViewById(R.id.linear_right);
        linear_answer = v.findViewById(R.id.linear_answer);
        linear_quick = v.findViewById(R.id.linear_quick);

        //???????????? ????????????
        View view1 , view2;   //???????????? ?????????????????????????????????
        TextView txt_danti , txt_daan;
        txt_danti = v.findViewById(R.id.txdanti);
        txt_daan = v.findViewById(R.id.txdaan);
        view1 = v.findViewById(R.id.view1);
        view2 = v.findViewById(R.id.view2);



        //????????????-????????????????????????-????????????
        tx_noanswer = v.findViewById(R.id.tx_noanswer);
        barChart = v.findViewById(R.id.bar_chart);
        barChart.setTouchEnabled(true); // ????????????????????????
        barChart.setDragEnabled(true);// ??????????????????
        barChart.setScaleEnabled(true);// ??????????????????

        slStusAnswers = v.findViewById(R.id.slStusAnswers);

        //??????????????????(??????????????????)
        List<String> listitem = new ArrayList<>();
        for (int i = 0; i < AnswerActivityTea.classList.size() ; i++) {
            String temp = AnswerActivityTea.classList.get(i).keTangName;
            listitem.add(temp);
        }

        //????????????Adapter
        myAdapter = new MyAdapter(listitem , this.getActivity() , selectedIndex , isSelect_huizong);
        lvClass.setAdapter(myAdapter);
        lvClass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                Object result = adapterView.getItemAtPosition(position);//?????????????????????
                selectedIndex = position;
                Log.e("????????????????????????1???  " ,  myAdapter.isSelect()+ "");
                myAdapter.setSelect(false);
                myAdapter.changeSelected(position);
                Log.e("????????????????????????2???  " ,  myAdapter.isSelect()+ "");
                if(isSelect_huizong){
                    tx_huizong.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    tx_huizong.setTextColor(Color.parseColor("#FF000000"));
                }
                isSelect_huizong = false;
                Log.e("???????????????index???123???  " , position + "");

                int aflag = 0;
                if(view1.getVisibility() == 0){  //??????????????????????????????
                    aflag = 1;
                    slStusAnswers.setVisibility(View.GONE);
                    tx_noanswer.setVisibility(View.GONE);
                    barChart.setVisibility(View.GONE);
                    barChart.setVisibility(View.VISIBLE);
                    barChart.getDescription().setEnabled(false); // ???????????????
                    barChart.setExtraOffsets(20, 20, 20, 20); // ????????????????????????????????????????????? ???????????????????????????
                }

                if(view2.getVisibility() == 0){ //?????????????????????????????????
                    aflag = 2;
                    tx_noanswer.setVisibility(View.GONE);
                    barChart.setVisibility(View.GONE);
                    slStusAnswers.setVisibility(View.VISIBLE);
//                    isShowMoreInformation(2 , selectedIndex);
                    //showStusAnswers(v); //????????????????????????
                }

                initHttpData_memberAnswer();
                System.out.println("????????????_class_1111:  " + AnswerActivityTea.answerStu);
                int count = AnswerActivityTea.classList.get(selectedIndex).stuNum;
                String classId = AnswerActivityTea.classList.get(selectedIndex).ketangId;
                getJoinClassMemberSubmitAnswerInf(classId , count , selectedIndex , aflag , v);
                System.out.println("????????????_class_2222:  " + AnswerActivityTea.answerStu);
            }
        });

        tx_huizong.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                isSelect_huizong = true;
                myAdapter.setmSelect(0);
                myAdapter.setSelect(true);
                tx_huizong.setBackgroundColor(Color.parseColor("#007947"));
                tx_huizong.setTextColor(Color.parseColor("#FFFFFF"));

                int aflag = 0;

                if(view1.getVisibility() == 0){  //??????????????????????????????
                    aflag = 1;
                    slStusAnswers.setVisibility(View.GONE);
                    tx_noanswer.setVisibility(View.GONE);
                    barChart.setVisibility(View.GONE);
                    barChart.setVisibility(View.VISIBLE);
                    barChart.getDescription().setEnabled(false); // ???????????????
                    barChart.setExtraOffsets(20, 20, 20, 20); // ????????????????????????????????????????????? ???????????????????????????
//                    isShowMoreInformation(1 , -1);
                }


                if(view2.getVisibility() == 0){ //?????????????????????????????????
                    aflag = 2;
                    barChart.setVisibility(View.GONE);
                    tx_noanswer.setVisibility(View.GONE);
                    slStusAnswers.setVisibility(View.VISIBLE);
//                    isShowMoreInformation(2 , -1);
//                    showStusAnswers(v); //????????????????????????
                }

                initHttpData_memberAnswer(); //????????????????????????????????????????????????
                System.out.println("??????????????????1111:  " + AnswerActivityTea.answerStu);
                int count = getJoinClassAllStuNum();
                //???????????????????????????????????????????????????
                getJoinClassMemberSubmitAnswerInf("all" , count , -1 , aflag , v);
                System.out.println("??????????????????22222:  " + AnswerActivityTea.answerStu);
            }
        });

        //????????????popupwindow
        if(flag == 1){  //"???????????????
            slStusAnswers.setVisibility(View.GONE);
            tx_noanswer.setVisibility(View.GONE);
            barChart.setVisibility(View.VISIBLE);
            barChart.getDescription().setEnabled(false); // ???????????????
            barChart.setExtraOffsets(20, 20, 20, 20); // ????????????????????????????????????????????? ???????????????????????????

            view1.setVisibility(View.VISIBLE);
            view2.setVisibility(View.GONE);
            txt_danti.setTextColor(Color.parseColor("#007947"));
            txt_daan.setTextColor(Color.parseColor("#FF000000"));
        }else{ //"????????????"
            barChart.setVisibility(View.GONE);
            tx_noanswer.setVisibility(View.GONE);
            slStusAnswers.setVisibility(View.VISIBLE);
            view1.setVisibility(View.GONE);
            view2.setVisibility(View.VISIBLE);
            txt_daan.setTextColor(Color.parseColor("#007947"));
            txt_danti.setTextColor(Color.parseColor("#FF000000"));

//            showStusAnswers(v); //????????????????????????
        }


        if(isSelect_huizong){
            //????????????popupwindow?????????????????????
            System.out.println("????????????11111:  " + AnswerActivityTea.answerStu);
            int count = getJoinClassAllStuNum();
            //???????????????????????????????????????????????????
            getJoinClassMemberSubmitAnswerInf("all" , count , -1 , flag , v);
            System.out.println("????????????22222:  " + AnswerActivityTea.answerStu);
        }else{
            initHttpData_memberAnswer();
            System.out.println("????????????_class_1111:  " + AnswerActivityTea.answerStu);
            int count = AnswerActivityTea.classList.get(selectedIndex).stuNum;
            String classId = AnswerActivityTea.classList.get(selectedIndex).ketangId;
            getJoinClassMemberSubmitAnswerInf(classId , count , selectedIndex , flag , v);
            System.out.println("????????????_class_2222:  " + AnswerActivityTea.answerStu);
        }



        txt_danti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedIndex = myAdapter.getmSelect();
                slStusAnswers.setVisibility(View.GONE);
                barChart.setVisibility(View.VISIBLE);
                barChart.getDescription().setEnabled(false); // ???????????????
                barChart.setExtraOffsets(20, 20, 20, 20); // ????????????????????????????????????????????? ???????????????????????????

                view1.setVisibility(View.VISIBLE);
                view2.setVisibility(View.GONE);
                txt_danti.setTextColor(Color.parseColor("#007947"));
                txt_daan.setTextColor(Color.parseColor("#FF000000"));

//                isShowMoreInformation(1 , selectedIndex);
                if(isSelect_huizong){
                    //????????????popupwindow?????????????????????
                    System.out.println("????????????11111:  " + AnswerActivityTea.answerStu);
                    int count = getJoinClassAllStuNum();
                    //???????????????????????????????????????????????????
                    getJoinClassMemberSubmitAnswerInf("all" , count , -1 , 1 , v);
                    System.out.println("????????????22222:  " + AnswerActivityTea.answerStu);
                }else{
                    initHttpData_memberAnswer();
                    System.out.println("????????????_class_1111:  " + AnswerActivityTea.answerStu);
                    int count = AnswerActivityTea.classList.get(selectedIndex).stuNum;
                    String classId = AnswerActivityTea.classList.get(selectedIndex).ketangId;
                    getJoinClassMemberSubmitAnswerInf(classId , count , selectedIndex , 1 , v);
                    System.out.println("????????????_class_2222:  " + AnswerActivityTea.answerStu);
                }
            }
        });

        txt_daan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedIndex = myAdapter.getmSelect();
                barChart.setVisibility(View.GONE);
                slStusAnswers.setVisibility(View.VISIBLE);
                view1.setVisibility(View.GONE);
                view2.setVisibility(View.VISIBLE);
                txt_daan.setTextColor(Color.parseColor("#007947"));
                txt_danti.setTextColor(Color.parseColor("#FF000000"));

                if(isSelect_huizong){
                    //????????????popupwindow?????????????????????
                    System.out.println("????????????11111:  " + AnswerActivityTea.answerStu);
                    int count = getJoinClassAllStuNum();
                    //???????????????????????????????????????????????????
                    getJoinClassMemberSubmitAnswerInf("all" , count , -1 , 2 , v);
                    System.out.println("????????????22222:  " + AnswerActivityTea.answerStu);
                }else{
                    initHttpData_memberAnswer();
                    System.out.println("????????????_class_1111:  " + AnswerActivityTea.answerStu);
                    int count = AnswerActivityTea.classList.get(selectedIndex).stuNum;
                    String classId = AnswerActivityTea.classList.get(selectedIndex).ketangId;
                    getJoinClassMemberSubmitAnswerInf(classId , count , selectedIndex , 2 , v);
                    System.out.println("????????????_class_2222:  " + AnswerActivityTea.answerStu);
                }
//                showStusAnswers(v); //????????????????????????
//                isShowMoreInformation(2 , selectedIndex);
            }
        });

        //??????????????????
        ImageView img_szda = v.findViewById(R.id.imgSet);
        //??????????????????
        ImageView img_gbjg = v.findViewById(R.id.imgPush);
        //????????????
        ImageView img_flash = v.findViewById(R.id.imgFlash);
        //????????????
        ImageView imgexit = v.findViewById(R.id.imgExit);

        img_szda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                LinearLayout linear1 = v.findViewById(R.id.linear1);
//                LinearLayout linear2 = v.findViewById(R.id.linear2);
//                LinearLayout linear3 = v.findViewById(R.id.linear3);
//                View ve = v.findViewById(R.id.view);
//
//                linear1.getBackground().setAlpha(10);
//                linear2.getBackground().setAlpha(10);
//                linear3.getBackground().setAlpha(10);
//                ve.getBackground().setAlpha(10);


//                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
//                lp.alpha = 0.2f; // 0.0~1.0
//                getActivity().getWindow().setAttributes(lp); //getActivity() ????????????context

                View v_self = LayoutInflater.from(getActivity()).inflate(R.layout.single_question_answers, null, false);
                if(isSelect_huizong){
                    setAnswers(v_self , v , popupWindow , flag , "all");
                }else{
                    setAnswers(v_self , v , popupWindow , flag , AnswerActivityTea.classList.get(selectedIndex).ketangId);
                }
            }
        });

        img_gbjg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                //???popupwindow??????????????????
                popupWindow.showAtLocation(v , Gravity.TOP | Gravity.LEFT , 0 , 0);
                //?????????????????????????????????????????????????????????????????????????????????
                img_szda.setPadding(200 , 0 , 0 , 0);
                img_gbjg.setVisibility(View.GONE);
                img_flash.setVisibility(View.GONE);
            }
        });

        img_flash.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                Log.e("?????????????????????", view.getId() + "");
                System.out.println("??????????????????????????????????????????????????????????????????");
                int position = myAdapter.getmSelect();
                int aflag = 0;
                if(view1.getVisibility() == 0){  //??????????????????????????????
                    aflag = 1;
                    slStusAnswers.setVisibility(View.GONE);
                    barChart.setVisibility(View.GONE);
                    barChart.setVisibility(View.VISIBLE);
                    barChart.getDescription().setEnabled(false); // ???????????????
                    barChart.setExtraOffsets(20, 20, 20, 20); // ????????????????????????????????????????????? ???????????????????????????
                }

                if(view2.getVisibility() == 0){ //?????????????????????????????????
                    aflag = 2;
                    barChart.setVisibility(View.GONE);
                    slStusAnswers.setVisibility(View.VISIBLE);
                }

                if(isSelect_huizong){
                    //????????????popupwindow?????????????????????
                    System.out.println("????????????11111:  " + AnswerActivityTea.answerStu);
                    int count = getJoinClassAllStuNum();
                    //???????????????????????????????????????????????????
                    getJoinClassMemberSubmitAnswerInf("all" , count , -1 , aflag , v);
                    System.out.println("????????????22222:  " + AnswerActivityTea.answerStu);
                }else{
                    initHttpData_memberAnswer();
                    System.out.println("????????????_class_1111:  " + AnswerActivityTea.answerStu);
                    int count = AnswerActivityTea.classList.get(selectedIndex).stuNum;
                    String classId = AnswerActivityTea.classList.get(selectedIndex).ketangId;
                    getJoinClassMemberSubmitAnswerInf(classId , count , selectedIndex , aflag , v);
                    System.out.println("????????????_class_2222:  " + AnswerActivityTea.answerStu);
                }
            }
        });

        imgexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                selectedIndex = 0;
                answer = "";
                isSelect_huizong = true;
            }
        });
    }

    // ???????????????
    private void setAxis(List<String> labelName) {
        System.out.println("??????????????????11111:   " + labelName);
        // ??????x???
        XAxis xAxis = barChart.getXAxis();
        xAxis.setYOffset(10); // ???????????????x??????????????????????????????
        // ??????y??????y?????????????????????????????????
        if(txModle_duoxuan.isSelected()){
            //??????X???????????????????????????????????????????????????
            barChart.getXAxis().setLabelRotationAngle(-50);
            xAxis.setYOffset(5); // ???????????????x??????????????????????????????
        }
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);  // ??????x????????????????????????????????????

        xAxis.setDrawGridLines(false); // ???????????????true??????????????????????????????
        xAxis.setLabelCount(labelName.size());  // ??????x?????????????????????
        xAxis.setTextSize(15f); // x?????????????????????
        xAxis.setAxisLineColor(Color.parseColor("#426ab3"));
        // ??????x????????????????????????
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if ((int) value < labelName.size()) {
                    return labelName.get((int) value);
                } else {
                    return "";
                }
            }
        });

        int max = 0;
        for(int i = 0; i < AnswerActivityTea.ylist.size() ; i++){
            if(AnswerActivityTea.ylist.get(i) > max){
                max = AnswerActivityTea.ylist.get(i);
            }
        }
        max += 1;

        YAxis yAxis_right = barChart.getAxisRight();
        yAxis_right.setAxisMaximum(max);  // ??????y???????????????
        yAxis_right.setAxisMinimum(0f);  // ??????y???????????????
        yAxis_right.setEnabled(false);  // ??????????????????y???

        YAxis yAxis_left = barChart.getAxisLeft();
        yAxis_left.setAxisMaximum(max);
        yAxis_left.setAxisMinimum(0f);
        yAxis_left.setTextSize(15f); // ??????y??????????????????
        yAxis_left.setAxisLineColor(Color.parseColor("#426ab3"));
        yAxis_left.setGridColor(Color.parseColor("#426ab3"));
//        yAxis_left.setGridLineWidth(1f);
    }

    // ????????????
    private void setLegend() {
        Legend legend = barChart.getLegend();
        legend.setEnabled(false);  //???????????????
//        legend.setFormSize(12f); // ?????????????????????
//        legend.setTextSize(15f); // ?????????????????????
//        legend.setDrawInside(true); // ?????????????????????
//        legend.setOrientation(Legend.LegendOrientation.VERTICAL); // ????????????????????????
//        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT); //??????????????????????????????
//        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP); // ??????????????????????????????
//        // ???????????????????????????????????????
//        legend.setYOffset(55f);
//        legend.setXOffset(30f);
    }

    //????????????
    private void setData(List<String> labelName , List<Integer> labelCount) {
        List<IBarDataSet> sets = new ArrayList<>();
        // ???????????????DataSet???????????????????????????BarEntry????????????x???y????????????????????????????????????
        // x??????????????????????????????y???????????????????????????
        List<BarEntry> barEntries1 = new ArrayList<>();
        for(int i = 0 ; i < labelCount.size() ; i++){
            barEntries1.add(new BarEntry(i, labelCount.get(i)));
        }
//        barEntries1.add(new BarEntry(0, 2f));
//        barEntries1.add(new BarEntry(1, 39f));
//        barEntries1.add(new BarEntry(2, 5f));
//        barEntries1.add(new BarEntry(3, 8f));
//        barEntries1.add(new BarEntry(4, 3f));
        BarDataSet barDataSet1 = new BarDataSet(barEntries1, "");
        barDataSet1.setValueTextColor(Color.BLACK); // ????????????
        barDataSet1.setValueTextSize(15f); // ????????????
//        barDataSet1.setColor(Color.parseColor("#1AE61A")); // ???????????????
        List<Integer> colors = new ArrayList<>();
        for(int i = 0 ; i < labelName.size() ; i++){
            if(labelName.get(i).equals(answer)){
                colors.add(Color.parseColor("#FF6100"));
            }else if(labelName.get(i) == "??????"){
                colors.add(Color.parseColor("#828798"));
            }else{
                colors.add(Color.parseColor("#4e72b8"));
            }
        }
        barDataSet1.setColors(colors);
//        barDataSet1.setColors(Color.parseColor("#FF6100"),
//                Color.parseColor("#485060"),
//                Color.parseColor("#FFC800"),
//                Color.parseColor("#694E42"),
//                Color.parseColor("#485060"));
//        barDataSet1.setLabel("??????"); // ?????????????????????????????????????????????????????????????????????
        // ????????????????????????????????????
        barDataSet1.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                // ?????????value????????????????????????
                return (int)value + "???";
            }
        });
        sets.add(barDataSet1);

        BarData barData = new BarData(sets);
        barData.setBarWidth(0.6f); // ?????????????????????
        barChart.setData(barData);
    }

    //????????????popupwindow????????????
    public void setAnswers(View v , View v_parent , PopupWindow p_parent , int flag , String ketangId){
        //???popupWindow???????????????????????????view??????popupWindow???
//        PopupWindow popupWindow_szda = new PopupWindow(v ,
//                450,
//                300,
//                false);
        if(screenWidth <= 0){
            getScreenProps();
        }
        PopupWindow popupWindow_szda = new PopupWindow(v , (int)(screenWidth * 0.45) , (int)(screenHeight * 0.4) , false);

        //???????????????????????????Gravity.CENTER,x???y????????????Gravity.CENTER?????????
        popupWindow_szda.showAtLocation(v , Gravity.CENTER , 0 , 0);

        //??????popupwindow
        ImageView closeImg = v.findViewById(R.id.imgClose);
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer = "";
                popupWindow_szda.dismiss();
            }
        });

        //??????UI
        linear_danxuan = v.findViewById(R.id.linearImg1);
        a = v.findViewById(R.id.a);
        b = v.findViewById(R.id.b);
        c = v.findViewById(R.id.c);
        d = v.findViewById(R.id.d);
        e = v.findViewById(R.id.e);
        f = v.findViewById(R.id.f);
        g = v.findViewById(R.id.g);
        h = v.findViewById(R.id.h);
        //??????UI
        linear_duoxuan = v.findViewById(R.id.linearImg2);
        a1 = v.findViewById(R.id.a1);
        b1 = v.findViewById(R.id.b1);
        c1 = v.findViewById(R.id.c1);
        d1 = v.findViewById(R.id.d1);
        e1 = v.findViewById(R.id.e1);
        f1 = v.findViewById(R.id.f1);
        g1 = v.findViewById(R.id.g1);
        h1 = v.findViewById(R.id.h1);
        //??????UI
        linear_panduan = v.findViewById(R.id.linearImg3);
        right = v.findViewById(R.id.right);
        error = v.findViewById(R.id.error);

//        int count = 0; //????????????
//        for(int i = 0 ; i < labelName.length ; i++){
//            if(labelName[i] != "??????"){
//                count++;
//            }
//        }

        int count = chooseNum; //????????????
//        int count = 8;
        //2-4?????????
        LinearLayout.LayoutParams lps1 = new LinearLayout.LayoutParams(90, 90);
//        LinearLayout.LayoutParams lps1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT , 1);
        lps1.setMargins(3 , 3 , 3 , 3);
        //5?????????
        LinearLayout.LayoutParams lps2 = new LinearLayout.LayoutParams(70, 70);
//        LinearLayout.LayoutParams lps2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT , 1);
        lps2.setMargins(3 , 3 , 3 , 3);
        //6?????????
        LinearLayout.LayoutParams lps3 = new LinearLayout.LayoutParams(65, 65);
//        LinearLayout.LayoutParams lps3 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT , 1);
        lps3.setMargins(3 , 3 , 3 , 3);
        //7?????????
        LinearLayout.LayoutParams lps4 = new LinearLayout.LayoutParams(55, 55);
//        LinearLayout.LayoutParams lps4 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT , 1);
        lps4.setMargins(3 , 3 , 3 , 3);
        //8?????????
        LinearLayout.LayoutParams lps5 = new LinearLayout.LayoutParams(49, 49);
//        LinearLayout.LayoutParams lps5 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT , 1);
        lps5.setMargins(3 , 3 , 3 , 3);
        //??????(?????????
        if(txModle_danxuan.isSelected()){
            linear_danxuan.setVisibility(View.VISIBLE);
            linear_duoxuan.setVisibility(View.GONE);
            linear_panduan.setVisibility(View.GONE);

            if(count == 2){
                a.setLayoutParams(lps1);
                b.setLayoutParams(lps1);
                c.setVisibility(View.GONE);
                d.setVisibility(View.GONE);
                e.setVisibility(View.GONE);
                f.setVisibility(View.GONE);
                g.setVisibility(View.GONE);
                h.setVisibility(View.GONE);
            }else if(count == 3){
                a.setLayoutParams(lps1);
                b.setLayoutParams(lps1);
                c.setLayoutParams(lps1);
                d.setVisibility(View.GONE);
                e.setVisibility(View.GONE);
                f.setVisibility(View.GONE);
                g.setVisibility(View.GONE);
                h.setVisibility(View.GONE);
            }else if(count == 4){
                a.setLayoutParams(lps1);
                b.setLayoutParams(lps1);
                c.setLayoutParams(lps1);
                d.setLayoutParams(lps1);
                e.setVisibility(View.GONE);
                f.setVisibility(View.GONE);
                g.setVisibility(View.GONE);
                h.setVisibility(View.GONE);
            }else if(count == 5){
                a.setLayoutParams(lps2);
                b.setLayoutParams(lps2);
                c.setLayoutParams(lps2);
                d.setLayoutParams(lps2);
                e.setLayoutParams(lps2);
                f.setVisibility(View.GONE);
                g.setVisibility(View.GONE);
                h.setVisibility(View.GONE);
            }else if(count == 6){
                a.setLayoutParams(lps3);
                b.setLayoutParams(lps3);
                c.setLayoutParams(lps3);
                d.setLayoutParams(lps3);
                e.setLayoutParams(lps3);
                f.setLayoutParams(lps3);
                g.setVisibility(View.GONE);
                h.setVisibility(View.GONE);
            }else if(count == 7){
                a.setLayoutParams(lps4);
                b.setLayoutParams(lps4);
                c.setLayoutParams(lps4);
                d.setLayoutParams(lps4);
                e.setLayoutParams(lps4);
                f.setLayoutParams(lps4);
                g.setLayoutParams(lps4);
                h.setVisibility(View.GONE);
            }else if(count == 8){
                a.setLayoutParams(lps5);
                b.setLayoutParams(lps5);
                c.setLayoutParams(lps5);
                d.setLayoutParams(lps5);
                e.setLayoutParams(lps5);
                f.setLayoutParams(lps5);
                g.setLayoutParams(lps5);
                h.setLayoutParams(lps5);
            }
        }
        //??????(?????????
        if(txModle_duoxuan.isSelected()){
            linear_danxuan.setVisibility(View.GONE);
            linear_panduan.setVisibility(View.GONE);
            linear_duoxuan.setVisibility(View.VISIBLE);
            if(count == 2){
                a1.setLayoutParams(lps1);
                b1.setLayoutParams(lps1);
                c1.setVisibility(View.GONE);
                d1.setVisibility(View.GONE);
                e1.setVisibility(View.GONE);
                f1.setVisibility(View.GONE);
                g1.setVisibility(View.GONE);
                h1.setVisibility(View.GONE);
            }else if(count == 3){
                a1.setLayoutParams(lps1);
                b1.setLayoutParams(lps1);
                c1.setLayoutParams(lps1);
                d1.setVisibility(View.GONE);
                e1.setVisibility(View.GONE);
                f1.setVisibility(View.GONE);
                g1.setVisibility(View.GONE);
                h1.setVisibility(View.GONE);
            }else if(count == 4){
                a1.setLayoutParams(lps1);
                b1.setLayoutParams(lps1);
                c1.setLayoutParams(lps1);
                d1.setLayoutParams(lps1);
                e1.setVisibility(View.GONE);
                f1.setVisibility(View.GONE);
                g1.setVisibility(View.GONE);
                h1.setVisibility(View.GONE);
            }else if(count == 5){
                a1.setLayoutParams(lps2);
                b1.setLayoutParams(lps2);
                c1.setLayoutParams(lps2);
                d1.setLayoutParams(lps2);
                e1.setLayoutParams(lps2);
                f1.setVisibility(View.GONE);
                g1.setVisibility(View.GONE);
                h1.setVisibility(View.GONE);
            }else if(count == 6){
                a1.setLayoutParams(lps3);
                b1.setLayoutParams(lps3);
                c1.setLayoutParams(lps3);
                d1.setLayoutParams(lps3);
                e1.setLayoutParams(lps3);
                f1.setLayoutParams(lps3);
                g1.setVisibility(View.GONE);
                h1.setVisibility(View.GONE);
            }else if(count == 7){
                a1.setLayoutParams(lps4);
                b1.setLayoutParams(lps4);
                c1.setLayoutParams(lps4);
                d1.setLayoutParams(lps4);
                e1.setLayoutParams(lps4);
                f1.setLayoutParams(lps4);
                g1.setLayoutParams(lps4);
                h1.setVisibility(View.GONE);
            }else if(count == 8){
                a1.setLayoutParams(lps5);
                b1.setLayoutParams(lps5);
                c1.setLayoutParams(lps5);
                d1.setLayoutParams(lps5);
                e1.setLayoutParams(lps5);
                f1.setLayoutParams(lps5);
                g1.setLayoutParams(lps5);
                h1.setLayoutParams(lps5);
            }
        }
        //??????(?????????
        if(txModle_panduan.isSelected()){
            linear_danxuan.setVisibility(View.GONE);
            linear_duoxuan.setVisibility(View.GONE);
            linear_panduan.setVisibility(View.VISIBLE);
        }

        //????????????????????????
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDanxuanImgStatus();
                a.setSelected(true);
                a.setImageDrawable(getResources().getDrawable((R.mipmap.a_select)));
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDanxuanImgStatus();
                b.setSelected(true);
                b.setImageDrawable(getResources().getDrawable((R.mipmap.b_select)));
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDanxuanImgStatus();
                c.setSelected(true);
                c.setImageDrawable(getResources().getDrawable((R.mipmap.c_select)));
            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDanxuanImgStatus();
                d.setSelected(true);
                d.setImageDrawable(getResources().getDrawable((R.mipmap.d_select)));
            }
        });
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDanxuanImgStatus();
                e.setSelected(true);
                e.setImageDrawable(getResources().getDrawable((R.mipmap.e_select)));
            }
        });
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDanxuanImgStatus();
                f.setSelected(true);
                f.setImageDrawable(getResources().getDrawable((R.mipmap.f_select)));
            }
        });
        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDanxuanImgStatus();
                g.setSelected(true);
                g.setImageDrawable(getResources().getDrawable((R.mipmap.g_select)));
            }
        });
        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDanxuanImgStatus();
                h.setSelected(true);
                h.setImageDrawable(getResources().getDrawable((R.mipmap.h_select)));
            }
        });
        //????????????????????????
        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //????????????imageView???????????????????????????????????????imageview??????????????????
                if((a1.getDrawable().getCurrent().getConstantState()).equals(
                        ContextCompat.getDrawable(getActivity(), R.mipmap.ad_select).getConstantState())
                ) {
                    a1.setSelected(false);
                    a1.setImageDrawable(getResources().getDrawable((R.mipmap.ad)));
                }else {
                    a1.setSelected(true);
                    a1.setImageDrawable(getResources().getDrawable((R.mipmap.ad_select)));
                }

            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((b1.getDrawable().getCurrent().getConstantState()).equals(ContextCompat.getDrawable(getActivity(), R.mipmap.bd_select).getConstantState())){
                    b1.setSelected(false);
                    b1.setImageDrawable(getResources().getDrawable((R.mipmap.bd)));
                }else{
                    b1.setSelected(true);
                    b1.setImageDrawable(getResources().getDrawable((R.mipmap.bd_select)));
                }
            }
        });
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((c1.getDrawable().getCurrent().getConstantState()).equals(ContextCompat.getDrawable(getActivity(), R.mipmap.cd_select).getConstantState())){
                    c1.setSelected(false);
                    c1.setImageDrawable(getResources().getDrawable((R.mipmap.cd)));
                }else{
                    c1.setSelected(true);
                    c1.setImageDrawable(getResources().getDrawable((R.mipmap.cd_select)));
                }
            }
        });
        d1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((d1.getDrawable().getCurrent().getConstantState()).equals(ContextCompat.getDrawable(getActivity(), R.mipmap.dd_select).getConstantState())){
                    d1.setSelected(false);
                    d1.setImageDrawable(getResources().getDrawable((R.mipmap.dd)));
                }else{
                    d1.setSelected(true);
                    d1.setImageDrawable(getResources().getDrawable((R.mipmap.dd_select)));
                }
            }
        });
        e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((e1.getDrawable().getCurrent().getConstantState()).equals(ContextCompat.getDrawable(getActivity(), R.mipmap.ed_select).getConstantState())){
                    e1.setSelected(false);
                    e1.setImageDrawable(getResources().getDrawable((R.mipmap.ed)));
                }else{
                    e1.setSelected(true);
                    e1.setImageDrawable(getResources().getDrawable((R.mipmap.ed_select)));
                }
            }
        });
        f1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((f1.getDrawable().getCurrent().getConstantState()).equals(ContextCompat.getDrawable(getActivity(), R.mipmap.fd_select).getConstantState())){
                    f1.setSelected(false);
                    f1.setImageDrawable(getResources().getDrawable((R.mipmap.fd)));
                }else{
                    f1.setSelected(true);
                    f1.setImageDrawable(getResources().getDrawable((R.mipmap.fd_select)));
                }
            }
        });
        g1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((g1.getDrawable().getCurrent().getConstantState()).equals(ContextCompat.getDrawable(getActivity(), R.mipmap.gd_select).getConstantState())){
                    g1.setSelected(false);
                    g1.setImageDrawable(getResources().getDrawable((R.mipmap.gd)));
                }else{
                    g1.setSelected(true);
                    g1.setImageDrawable(getResources().getDrawable((R.mipmap.gd_select)));
                }
            }
        });
        h1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((h1.getDrawable().getCurrent().getConstantState()).equals(ContextCompat.getDrawable(getActivity(), R.mipmap.hd_select).getConstantState())){
                    h1.setSelected(false);
                    h1.setImageDrawable(getResources().getDrawable((R.mipmap.hd)));
                }else{
                    h1.setSelected(true);
                    h1.setImageDrawable(getResources().getDrawable((R.mipmap.hd_select)));
                }
            }
        });
        //????????????????????????
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                right.setSelected(true);
                error.setSelected(false);
                right.setImageDrawable(getResources().getDrawable((R.mipmap.right_select)));
                error.setImageDrawable(getResources().getDrawable((R.mipmap.error)));
            }
        });
        error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                right.setSelected(false);
                error.setSelected(true);
                right.setImageDrawable(getResources().getDrawable((R.mipmap.right)));
                error.setImageDrawable(getResources().getDrawable((R.mipmap.error_select)));
            }
        });

        Button bt_save = v.findViewById(R.id.btSave);
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txModle_danxuan.isSelected()) {
                    answer = "";
                    if (a.isSelected()) {
                        answer = "A";
                    } else if (b.isSelected()) {
                        answer = "B";
                    } else if (c.isSelected()) {
                        answer = "C";
                    } else if (d.isSelected()) {
                        answer = "D";
                    } else if (e.isSelected()) {
                        answer = "E";
                    } else if (f.isSelected()) {
                        answer = "F";
                    } else if (g.isSelected()) {
                        answer = "G";
                    } else if (h.isSelected()) {
                        answer = "H";
                    } else {
                        answer = "";
                    }
//                    Toast.makeText(getActivity(), "??????????????????" + answer, Toast.LENGTH_SHORT).show();
                } else if (txModle_duoxuan.isSelected()) {
                    answer = "";
                    if (a1.isSelected()) {
                        answer = answer + "A";
                    }
                    if (b1.isSelected()) {
                        answer = answer + "B";
                    }
                    if (c1.isSelected()) {
                        answer = answer + "C";
                    }
                    if (d1.isSelected()) {
                        answer = answer + "D";
                    }
                    if (e1.isSelected()) {
                        answer = answer + "E";
                    }
                    if (f1.isSelected()) {
                        answer = answer + "F";
                    }
                    if (g1.isSelected()) {
                        answer = answer + "G";
                    }
                    if (h1.isSelected()) {
                        answer = answer + "H";
                    }
//                    Toast.makeText(getActivity(), "??????????????????" + answer, Toast.LENGTH_SHORT).show();
                } else if (txModle_panduan.isSelected()) {
                    answer = "";
                    if (right.isSelected()) {
                        answer = "???";
                    } else if (error.isSelected()) {
                        answer = "???";
                    } else {
                        answer = "";
                    }
//                    Toast.makeText(getActivity(), "??????????????????" + answer, Toast.LENGTH_SHORT).show();
                }

                if(answer.length() > 0){
                    if((txModle_duoxuan.isSelected() && answer.length() >= 2)
                            || txModle_panduan.isSelected()
                            || txModle_danxuan.isSelected()
                    ){
                        setAnswer_keguan(ketangId);
                        //??????????????????wait 500ms
                        synchronized (Thread.currentThread()){
                            try {
                                Thread.currentThread().wait(500);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                        System.out.println("???????????????????????????????????????");
                        if(setAnswer_status){
                            Toast.makeText(getActivity(),"??????????????????",Toast.LENGTH_SHORT).show();
                            popupWindow_szda.dismiss(); //?????????????????????????????????popupwindow
                            p_parent.dismiss(); //?????????????????????????????????popupwindow
                            //???????????????????????????????????????popupwindow
                            showPopupWindow(v_parent, flag);
                        }else{
                            answer = "";
                            Toast.makeText(getActivity(),"??????????????????",Toast.LENGTH_SHORT).show();
                        }
//                        Toast.makeText(getActivity(),"?????????" + answer ,Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(),"???????????????",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(),"???????????????",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //?????????????????????????????????
    private void setAnswer_keguan(String ketangId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                setAnswer_status = Http_HuDongActivityTea.setAnswer(ketangId , answer);
            }
        }).start();
    }

    //??????????????????
    public void setDanxuanImgStatus(){
        a.setImageDrawable(getResources().getDrawable((R.mipmap.a)));
        b.setImageDrawable(getResources().getDrawable((R.mipmap.b)));
        c.setImageDrawable(getResources().getDrawable((R.mipmap.c)));
        d.setImageDrawable(getResources().getDrawable((R.mipmap.d)));
        e.setImageDrawable(getResources().getDrawable((R.mipmap.e)));
        f.setImageDrawable(getResources().getDrawable((R.mipmap.f)));
        g.setImageDrawable(getResources().getDrawable((R.mipmap.g)));
        h.setImageDrawable(getResources().getDrawable((R.mipmap.h)));
        a.setSelected(false);
        b.setSelected(false);
        c.setSelected(false);
        d.setSelected(false);
        e.setSelected(false);
        f.setSelected(false);
        g.setSelected(false);
        h.setSelected(false);
    }


    //????????????????????????-?????????
    private void initHttpData_memberAnswer_zhuguan(){
        AnswerActivityTea.submitAnswerStatus_zhuguan = false;
        AnswerActivityTea.alist_zhuguan = null;
    }

    //???http???????????????????????????????????????????????????????????????????????????(?????????????????????5???
    private void updateHttpData_memberAnswer_zhuguan(JSONObject jsonObject) throws JSONException{
        AnswerActivityTea.submitAnswerStatus_zhuguan = jsonObject.getBoolean("status");
        //?????????????????????
        JSONArray array_list = jsonObject.getJSONArray("list");
        if(AnswerActivityTea.alist_zhuguan != null){
            AnswerActivityTea.alist_zhuguan.clear();
        }
        if(AnswerActivityTea.alist_zhuguan == null){
            AnswerActivityTea.alist_zhuguan = new ArrayList<>();
        }
        for(int i = 0 ; i < array_list.length() ; i++){
            StuAnswerBean item = new StuAnswerBean();
            item.questionId = array_list.getJSONObject(i).getString("questionId");
            item.name = array_list.getJSONObject(i).getString("name");
            item.userId = array_list.getJSONObject(i).getString("userId");
            item.stuAnswer = array_list.getJSONObject(i).getString("stuAnswer");
            item.stuAnswerTime = array_list.getJSONObject(i).getLong("stuAnswerTime");
            item.startAnswerTime = array_list.getJSONObject(i).getLong("startAnswerTime");
            AnswerActivityTea.alist_zhuguan.add(item);
            System.out.println("item==>" + item.toString());
        }
        System.out.println("list???????????????????????????==>" + AnswerActivityTea.alist_zhuguan.size());

        setStuAnswers_zhuguan();
    }

    //??????????????????????????????????????????List<String> stusNameList ??? List<String> stusAnswerList
    private void setStuAnswers_zhuguan(){
        System.out.println("?????????????????????????????????????????????????????????");
        if(stusNameList != null){
            stusNameList.clear();
        }else{
            stusNameList = new ArrayList<>();
        }

        if(stusAnswerList != null){
            stusAnswerList.clear();
        }else{
            stusAnswerList = new ArrayList<>();
        }

        for(int i = 0; i < AnswerActivityTea.alist_zhuguan.size() ; i++){
            stusNameList.add(AnswerActivityTea.alist_zhuguan.get(i).name);
            stusAnswerList.add(AnswerActivityTea.alist_zhuguan.get(i).stuAnswer);
            System.out.println("????????????: " + stusNameList.get(i) + "  ,  ????????????: " + stusAnswerList.get(i));
        }
    }

    //???????????????????????????????????????????????????(?????????????????????) aflag:????????????-1???????????????-2 index:???????????????-1?????????????????????????????????index  v:???????????????popupwindow
    private void getJoinClassMemberSubmitAnswerInf_zhuguan(String ketangId , int index , int aflag , View v){
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("?????????: ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
                initHttpData_memberAnswer_zhuguan();
                System.out.println("???????????????????????????111:  " + AnswerActivityTea.alist_zhuguan);
                JSONObject jsonObject = Http_HuDongActivityTea.getSubmitAnswerClass_zhuguan(ketangId);
                String status = "";
                if(jsonObject != null){
                    try {
                        updateHttpData_memberAnswer_zhuguan(jsonObject);
                        status = jsonObject.getString("status");
                        System.out.println("???????????????????????????222:  " + status);
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
                if(jsonObject != null && status != null && status.length() > 0){
                    System.out.println("???????????????????????????333:  " + AnswerActivityTea.alist_zhuguan);
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            //???????????????
                            if(ketangId.equals("all")){
                                int all_count = getJoinClassAllStuNum();
                                int answer_count = AnswerActivityTea.alist_zhuguan.size();
                                tx_dati2.setText((int)(answer_count * 1.0 / all_count * 100) + "%");
                                System.out.println("?????????:  " + all_count + " ,  ????????????:  " + answer_count + " ,  ?????????:  " + (int)(answer_count * 1.0 / all_count * 100));
                            }else{
                                int all_count = AnswerActivityTea.classList.get(index).stuNum;
                                int answer_count = AnswerActivityTea.alist_zhuguan.size();
                                tx_dati2.setText((int)(answer_count * 1.0 / all_count * 100) + "%");
                                System.out.println("?????????:  " + all_count + " ,  ????????????:  " + answer_count + " ,  ?????????:  " + (int)(answer_count * 1.0 / all_count * 100));
                            }
                            tx_noanswer_zhuguan.setVisibility(View.GONE);
                            if(aflag == 1){ //????????????
                                showStudentsAnswer_img(v);
                            }else{ //????????????
                                showStudentsAnswer(v);
                            }
                        }
                    });
                }else{
//                    Toast.makeText(getActivity(), "?????????????????????" + answer, Toast.LENGTH_SHORT).show();
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            slStusAnswers_zhuguan.setVisibility(View.GONE);
                            slStusAnswersImg.setVisibility(View.GONE);
                            tx_noanswer_zhuguan.setVisibility(View.VISIBLE);
                            tx_dati2.setText("0%");
                        }
                    });
                }
            }
        }).start();
    }

    //???http???????????????????????????????????????????????????????????????????????????????????????-???????????????
    private void updateHttpData_memberAnswer_content(JSONObject jsonObject) throws JSONException {
        //??????????????????
        boolean status = jsonObject.getBoolean("status");
        AnswerActivityTea.submitAnswerStatus = status;
        System.out.println("????????????????????????==>" + AnswerActivityTea.submitAnswerStatus);

        //?????????????????????
        JSONArray array_alist = jsonObject.getJSONArray("alist");
        if(AnswerActivityTea.alist != null){
            AnswerActivityTea.alist.clear();
        }
        if(AnswerActivityTea.alist == null){
            AnswerActivityTea.alist = new ArrayList<>();
        }
        for(int i = 0 ; i < array_alist.length() ; i++){
            StuAnswerBean item = new StuAnswerBean();
            item.questionId = array_alist.getJSONObject(i).getString("questionId");
            item.name = array_alist.getJSONObject(i).getString("name");
            item.userId = array_alist.getJSONObject(i).getString("userId");
            item.stuAnswer = array_alist.getJSONObject(i).getString("stuAnswer");
            item.stuAnswerTime = array_alist.getJSONObject(i).getLong("stuAnswerTime");
            item.startAnswerTime = array_alist.getJSONObject(i).getLong("startAnswerTime");
            AnswerActivityTea.alist.add(item);
            System.out.println("item==>" + item.toString());
        }
        System.out.println("alist???????????????????????????==>" + AnswerActivityTea.alist.size());

        //??????????????????
        String answerStu = jsonObject.getString("answerStu");
        AnswerActivityTea.answerStu = answerStu;
        System.out.println("??????????????????==>" + AnswerActivityTea.answerStu);

        //??????????????????????????????List<List<String>> stusAnswerList_name???List<String> stusAnswerList_answer
        splitStuAnswers_zhuguan();
    }

    //??????????????????-?????????
    // List<List<String>> stusAnswerList_name_txt???List<String> stusAnswerList_answer_txt
    //List<String> stusAnswerList_name_img;  List<String> stusAnswerList_answer_img;
    private void splitStuAnswers_zhuguan(){
        System.out.println("??????????????????????????????????????????????????????????????????");
        //AnswerActivityTea.answerStu??????:
        // D:??????_2,??????_5,?????????,@#@A:??????_3,@#@B:??????_1,?????????,?????????,@#@C:??????_0,??????_4,?????????,@#@
        String stuAnswers = AnswerActivityTea.answerStu;
        System.out.println("????????????1111:" + stuAnswers);
        String[] answer_names_list = stuAnswers.split(",@#@");
        for(int i = 0 ; i < answer_names_list.length ; i++){
            System.out.println("????????????2222:" + answer_names_list[i]);
        }

        //????????????-??????
        if(stusAnswerList_answer_txt != null){
            stusAnswerList_answer_txt.clear();
        }else{
            stusAnswerList_answer_txt = new ArrayList<>();
        }
        //??????????????????-??????
        if(stusAnswerList_name_txt != null){
            stusAnswerList_name_txt.clear();
        }else{
            stusAnswerList_name_txt = new ArrayList<List<String>>();
        }

        //????????????-img
        if(stusAnswerList_answer_img != null){
            stusAnswerList_answer_img.clear();
        }else{
            stusAnswerList_answer_img = new ArrayList<>();
        }
        //??????????????????-img
        if(stusAnswerList_name_img != null){
            stusAnswerList_name_img.clear();
        }else{
            stusAnswerList_name_img = new ArrayList<>();
        }
        for(int i = 0 ; i < answer_names_list.length ; i++){
            String answer = answer_names_list[i].substring(0 , answer_names_list[i].indexOf(":"));
            String names = answer_names_list[i].substring(answer_names_list[i].indexOf(":") + 1);
            String[] name_list = names.split(",");
            //?????????????????????
            if(answer.indexOf("img") >= 0){
                stusAnswerList_answer_img.add(answer);
                for(int j = 0 ; j < name_list.length ; j++){
                    stusAnswerList_name_img.add(name_list[j]);
                }
            }else{
                stusAnswerList_answer_txt.add(answer);
                List<String> name_List = new ArrayList<String>(Arrays.asList(name_list));
                stusAnswerList_name_txt.add(name_List);
            }
        }
        System.out.println("stusAnswerList_name_txt: " + stusAnswerList_name_txt);
        System.out.println("stusAnswerList_answer_txt: " + stusAnswerList_answer_txt);
        System.out.println("stusAnswerList_name_img: " + stusAnswerList_name_img);
        System.out.println("stusAnswerList_answer_img: " + stusAnswerList_answer_img);
    }

    //???????????????????????????????????????????????????(?????????????????????) aflag:????????????-1???????????????-2 index:???????????????-1?????????????????????????????????index  v:???????????????popupwindow
    private void getJoinClassMemberSubmitAnswerInf_zhuguan_content(String ketangId , int stuNum ,  int index , int aflag , View v){
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("?????????: ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
                initHttpData_memberAnswer();
                System.out.println("???????????????????????????111:  " + AnswerActivityTea.alist);
                JSONObject jsonObject = Http_HuDongActivityTea.getSubmitAnswerClass_keguan(ketangId , stuNum);
                String status = "";
                if(jsonObject != null){
                    try {
                        updateHttpData_memberAnswer_content(jsonObject);
                        status = jsonObject.getString("status");
                        System.out.println("???????????????????????????222:  " + status);
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
                if(jsonObject != null && status != null && status.length() > 0){
                    System.out.println("???????????????????????????333:  " + AnswerActivityTea.alist);
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            //???????????????
                            if(ketangId.equals("all")){
                                int all_count = getJoinClassAllStuNum();
                                int answer_count = AnswerActivityTea.alist.size();
                                tx_dati2.setText((int)(answer_count * 1.0 / all_count * 100) + "%");
                                System.out.println("?????????:  " + all_count + " ,  ????????????:  " + answer_count + " ,  ?????????:  " + (int)(answer_count * 1.0 / all_count * 100));
                            }else{
                                int all_count = AnswerActivityTea.classList.get(index).stuNum;
                                int answer_count = AnswerActivityTea.alist.size();
                                tx_dati2.setText((int)(answer_count * 1.0 / all_count * 100) + "%");
                                System.out.println("?????????:  " + all_count + " ,  ????????????:  " + answer_count + " ,  ?????????:  " + (int)(answer_count * 1.0 / all_count * 100));
                            }
                            tx_noanswer_zhuguan.setVisibility(View.GONE);
                            if(aflag == 1){ //????????????
                                showStudentsAnswer_img(v);
                            }else{ //????????????
                                showStudentsAnswer(v);
                            }
                        }
                    });
                }else{
//                    Toast.makeText(getActivity(), "?????????????????????" + answer, Toast.LENGTH_SHORT).show();
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            slStusAnswers_zhuguan.setVisibility(View.GONE);
                            slStusAnswersImg.setVisibility(View.GONE);
                            tx_noanswer_zhuguan.setVisibility(View.VISIBLE);
                            tx_dati2.setText("0%");
                        }
                    });
                }
            }
        }).start();
    }


    //????????????(?????????)
    private void showPopupWindow_luru(View v , int flag){
        //???popupWindow???????????????????????????view??????popupWindow???
//        PopupWindow popupWindow = new PopupWindow(v ,
//                1000,
//                650,
//                false);
        initJoinClassInformation(); //???????????????????????????
        if(screenWidth <= 0){
            getScreenProps();
        }
        PopupWindow popupWindow = new PopupWindow(v , (int)(screenWidth * 0.75) , (int)(screenHeight * 0.8) , false);

        //???????????????????????????Gravity.CENTER,x???y????????????Gravity.CENTER?????????
        popupWindow.showAtLocation(v , Gravity.CENTER , 0 , 0);

        //???????????? ????????????
        View view1 , view2;   //???????????? ?????????????????????????????????
        TextView txt_daan , txt_dati;
        txt_daan = v.findViewById(R.id.txdaan);
        txt_dati = v.findViewById(R.id.txdati);
        view1 = v.findViewById(R.id.view1);
        view2 = v.findViewById(R.id.view2);

        slStusAnswersImg = v.findViewById(R.id.slStusAnswersImg);
        slStusAnswers_zhuguan = v.findViewById(R.id.slStusAnswers);
        tx_noanswer_zhuguan = v.findViewById(R.id.tx_noanswer);

        tx_dati1 = v.findViewById(R.id.tx_dati1);
        tx_dati2 = v.findViewById(R.id.tx_dati2);


        //????????????????????????????????????
        tx_huizong = v.findViewById(R.id.tx_huizong);
        ListView lvClass = v.findViewById(R.id.lvClass);
        lvClass.setDivider(null); //???????????????

        //??????????????????(??????????????????)
        List<String> listitem = new ArrayList<>();
        for (int i = 0; i < AnswerActivityTea.classList.size() ; i++) {
            String temp = AnswerActivityTea.classList.get(i).keTangName;
            listitem.add(temp);
        }

        //????????????Adapter
        myAdapter = new MyAdapter(listitem , this.getActivity() , selectedIndex , isSelect_huizong);
        lvClass.setAdapter(myAdapter);
        lvClass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                selectedIndex = position;
                Log.e("????????????????????????1???  " ,  myAdapter.isSelect()+ "");
                myAdapter.setSelect(false);
                myAdapter.changeSelected(position);
                Log.e("????????????????????????2???  " ,  myAdapter.isSelect()+ "");
                if(isSelect_huizong){
                    tx_huizong.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    tx_huizong.setTextColor(Color.parseColor("#FF000000"));
                }
                isSelect_huizong = false;
                Log.e("???????????????index???123???  " , position + "");

                int aflag = 0;
                if(view1.getVisibility() == 0){  //?????????????????????webview
                    aflag = 1;
                    view1.setVisibility(View.VISIBLE);
                    view2.setVisibility(View.GONE);
                    txt_daan.setTextColor(Color.parseColor("#007947"));
                    txt_dati.setTextColor(Color.parseColor("#FF000000"));
                    slStusAnswersImg.setVisibility(View.VISIBLE);
                    slStusAnswers_zhuguan.setVisibility(View.GONE);
                    tx_noanswer_zhuguan.setVisibility(View.GONE);
                }

                if(view2.getVisibility() == 0){ //?????????????????????????????????
                    aflag = 2;
                    view1.setVisibility(View.GONE);
                    view2.setVisibility(View.VISIBLE);
                    txt_daan.setTextColor(Color.parseColor("#FF000000"));
                    txt_dati.setTextColor(Color.parseColor("#007947"));
                    slStusAnswersImg.setVisibility(View.GONE);
                    slStusAnswers_zhuguan.setVisibility(View.VISIBLE);
                    tx_noanswer_zhuguan.setVisibility(View.GONE);
                }

                String classId = AnswerActivityTea.classList.get(selectedIndex).ketangId;
                if(aflag == 1){
                    System.out.println("?????????????????????_class_1111:  " + AnswerActivityTea.alist_zhuguan);
                    getJoinClassMemberSubmitAnswerInf_zhuguan(classId , selectedIndex , aflag , v);
                    System.out.println("?????????????????????_class_22222:  " + AnswerActivityTea.alist_zhuguan);
                }else{
                    System.out.println("?????????????????????_class_11111:  " + AnswerActivityTea.alist);
                    int count = AnswerActivityTea.classList.get(selectedIndex).stuNum;
                    getJoinClassMemberSubmitAnswerInf_zhuguan_content(classId , count ,  selectedIndex , aflag , v);
                    System.out.println("?????????????????????_class_22222:  " + AnswerActivityTea.alist);
                }
            }
        });

        //????????????popupwindow
        if(flag == 1){  //"???????????????
            slStusAnswersImg.setVisibility(View.VISIBLE);
            slStusAnswers_zhuguan.setVisibility(View.GONE);
            tx_noanswer_zhuguan.setVisibility(View.GONE);
            view1.setVisibility(View.VISIBLE);
            view2.setVisibility(View.GONE);
            txt_daan.setTextColor(Color.parseColor("#007947"));
            txt_dati.setTextColor(Color.parseColor("#FF000000"));
//            showStudentsAnswer_img(v);
        }else{ //"????????????"
            slStusAnswersImg.setVisibility(View.GONE);
            slStusAnswers_zhuguan.setVisibility(View.VISIBLE);
            tx_noanswer_zhuguan.setVisibility(View.GONE);
            view1.setVisibility(View.GONE);
            view2.setVisibility(View.VISIBLE);
            txt_daan.setTextColor(Color.parseColor("#FF000000"));
            txt_dati.setTextColor(Color.parseColor("#007947"));
//            showStudentsAnswer(v);
        }

        //????????????popupwindow?????????????????????
        if(isSelect_huizong){
            //???????????????????????????????????????????????????
            if(flag == 1){
                System.out.println("?????????????????????11111:  " + AnswerActivityTea.alist_zhuguan);
                getJoinClassMemberSubmitAnswerInf_zhuguan("all" , -1 , flag , v);
                System.out.println("?????????????????????22222:  " + AnswerActivityTea.alist_zhuguan);
            }else{
                System.out.println("?????????????????????11111:  " + AnswerActivityTea.alist);
                int count = getJoinClassAllStuNum();
                getJoinClassMemberSubmitAnswerInf_zhuguan_content("all" , count ,  -1 , flag , v);
                System.out.println("?????????????????????22222:  " + AnswerActivityTea.alist);
            }
        }else{
            String classId = AnswerActivityTea.classList.get(selectedIndex).ketangId;
            if(flag == 1){
                System.out.println("?????????????????????_class_1111:  " + AnswerActivityTea.alist_zhuguan);
                getJoinClassMemberSubmitAnswerInf_zhuguan(classId , selectedIndex , flag , v);
                System.out.println("?????????????????????_class_22222:  " + AnswerActivityTea.alist_zhuguan);
            }else{
                System.out.println("?????????????????????_class_11111:  " + AnswerActivityTea.alist);
                int count = AnswerActivityTea.classList.get(selectedIndex).stuNum;
                getJoinClassMemberSubmitAnswerInf_zhuguan_content(classId , count ,  selectedIndex , flag , v);
                System.out.println("?????????????????????_class_22222:  " + AnswerActivityTea.alist);
            }
        }


        tx_huizong.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                isSelect_huizong = true;
                myAdapter.setmSelect(0);
                myAdapter.setSelect(true);
                tx_huizong.setBackgroundColor(Color.parseColor("#007947"));
                tx_huizong.setTextColor(Color.parseColor("#FFFFFF"));
                int aflag = 0;
                if(view1.getVisibility() == 0){  //?????????????????????webview
                    aflag = 1;
                    view1.setVisibility(View.VISIBLE);
                    view2.setVisibility(View.GONE);
                    txt_daan.setTextColor(Color.parseColor("#007947"));
                    txt_dati.setTextColor(Color.parseColor("#FF000000"));
                    slStusAnswersImg.setVisibility(View.VISIBLE);
                    slStusAnswers_zhuguan.setVisibility(View.GONE);
                    tx_noanswer_zhuguan.setVisibility(View.GONE);
                }

                if(view2.getVisibility() == 0){ //?????????????????????????????????
                    aflag = 2;
                    view1.setVisibility(View.GONE);
                    view2.setVisibility(View.VISIBLE);
                    txt_daan.setTextColor(Color.parseColor("#FF000000"));
                    txt_dati.setTextColor(Color.parseColor("#007947"));
                    slStusAnswersImg.setVisibility(View.GONE);
                    slStusAnswers_zhuguan.setVisibility(View.VISIBLE);
                    tx_noanswer_zhuguan.setVisibility(View.GONE);
                }

                //???????????????????????????????????????????????????
                if(aflag == 1){
                    System.out.println("?????????????????????11111:  " + AnswerActivityTea.alist_zhuguan);
                    getJoinClassMemberSubmitAnswerInf_zhuguan("all" , -1 , aflag , v);
                    System.out.println("?????????????????????22222:  " + AnswerActivityTea.alist_zhuguan);
                }else{
                    System.out.println("?????????????????????11111:  " + AnswerActivityTea.alist);
                    int count = getJoinClassAllStuNum();
                    getJoinClassMemberSubmitAnswerInf_zhuguan_content("all" , count ,  -1 , aflag , v);
                    System.out.println("?????????????????????22222:  " + AnswerActivityTea.alist);
                }
            }
        });

        txt_daan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view1.setVisibility(View.VISIBLE);
                view2.setVisibility(View.GONE);
                txt_daan.setTextColor(Color.parseColor("#007947"));
                txt_dati.setTextColor(Color.parseColor("#FF000000"));
//                showStudentsAnswer_img(v);
                if(isSelect_huizong){
                    //???????????????????????????????????????????????????
                    System.out.println("?????????????????????11111:  " + AnswerActivityTea.alist_zhuguan);
                    getJoinClassMemberSubmitAnswerInf_zhuguan("all" , -1 , 1 , v);
                    System.out.println("?????????????????????22222:  " + AnswerActivityTea.alist_zhuguan);
                }else{
                    System.out.println("?????????????????????_class_1111:  " + AnswerActivityTea.alist_zhuguan);
                    String classId = AnswerActivityTea.classList.get(selectedIndex).ketangId;
                    getJoinClassMemberSubmitAnswerInf_zhuguan(classId , selectedIndex , 1 , v);
                    System.out.println("?????????????????????_class_22222:  " + AnswerActivityTea.alist_zhuguan);
                }
            }
        });

        txt_dati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view1.setVisibility(View.GONE);
                view2.setVisibility(View.VISIBLE);
                txt_daan.setTextColor(Color.parseColor("#FF000000"));
                txt_dati.setTextColor(Color.parseColor("#007947"));
//                showStudentsAnswer(v);
                if(isSelect_huizong){
                    //???????????????????????????????????????????????????
                    System.out.println("?????????????????????11111:  " + AnswerActivityTea.alist);
                    int count = getJoinClassAllStuNum();
                    getJoinClassMemberSubmitAnswerInf_zhuguan_content("all" , count ,  -1 , 2 , v);
                    System.out.println("?????????????????????22222:  " + AnswerActivityTea.alist);
                }else{
                    System.out.println("?????????????????????_class_11111:  " + AnswerActivityTea.alist);
                    String classId = AnswerActivityTea.classList.get(selectedIndex).ketangId;
                    int count = AnswerActivityTea.classList.get(selectedIndex).stuNum;
                    getJoinClassMemberSubmitAnswerInf_zhuguan_content(classId , count ,  selectedIndex , 2 , v);
                    System.out.println("?????????????????????_class_22222:  " + AnswerActivityTea.alist);
                }
            }
        });

        //??????????????????
        ImageView img_gbjg = v.findViewById(R.id.imgPush);
        //????????????
        ImageView img_flash = v.findViewById(R.id.imgFlash);
        //????????????
        ImageView img_exit = v.findViewById(R.id.imgExit);


        img_gbjg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                //???popupwindow??????????????????
                popupWindow.showAtLocation(v , Gravity.TOP | Gravity.LEFT , 0 , 0);
                //???????????????????????????????????????????????????,????????????????????????
                img_gbjg.setVisibility(View.INVISIBLE);
                img_flash.setVisibility(View.INVISIBLE);
            }
        });

        img_flash.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                System.out.println("?????????????????????!!!!!!!!!!!!!!!");
                Log.e("?????????????????????", view.getId() + "");
                int position = myAdapter.getmSelect();
                int aflag = 0;
                if(view1.getVisibility() == 0){  //?????????????????????webview
                    aflag = 1;
                    view1.setVisibility(View.VISIBLE);
                    view2.setVisibility(View.GONE);
                    txt_daan.setTextColor(Color.parseColor("#007947"));
                    txt_dati.setTextColor(Color.parseColor("#FF000000"));
                    slStusAnswersImg.setVisibility(View.VISIBLE);
                    slStusAnswers_zhuguan.setVisibility(View.GONE);
                    tx_noanswer_zhuguan.setVisibility(View.GONE);
                }

                if(view2.getVisibility() == 0){ //?????????????????????????????????
                    aflag = 2;
                    view1.setVisibility(View.GONE);
                    view2.setVisibility(View.VISIBLE);
                    txt_daan.setTextColor(Color.parseColor("#FF000000"));
                    txt_dati.setTextColor(Color.parseColor("#007947"));
                    slStusAnswersImg.setVisibility(View.GONE);
                    slStusAnswers_zhuguan.setVisibility(View.VISIBLE);
                    tx_noanswer_zhuguan.setVisibility(View.GONE);
                }

                if(isSelect_huizong){
                    //???????????????????????????????????????????????????
                    if(aflag == 1){
                        System.out.println("?????????????????????11111:  " + AnswerActivityTea.alist_zhuguan);
                        getJoinClassMemberSubmitAnswerInf_zhuguan("all" , -1 , aflag , v);
                        System.out.println("?????????????????????22222:  " + AnswerActivityTea.alist_zhuguan);
                    }else{
                        System.out.println("?????????????????????11111:  " + AnswerActivityTea.alist);
                        int count = getJoinClassAllStuNum();
                        getJoinClassMemberSubmitAnswerInf_zhuguan_content("all" , count ,  -1 , aflag , v);
                        System.out.println("?????????????????????22222:  " + AnswerActivityTea.alist);
                    }
                }else{
                    String classId = AnswerActivityTea.classList.get(selectedIndex).ketangId;
                    if(aflag == 1){
                        System.out.println("?????????????????????_class_1111:  " + AnswerActivityTea.alist_zhuguan);
                        getJoinClassMemberSubmitAnswerInf_zhuguan(classId , selectedIndex , aflag , v);
                        System.out.println("?????????????????????_class_22222:  " + AnswerActivityTea.alist_zhuguan);
                    }else{
                        System.out.println("?????????????????????_class_11111:  " + AnswerActivityTea.alist);
                        int count = AnswerActivityTea.classList.get(selectedIndex).stuNum;
                        getJoinClassMemberSubmitAnswerInf_zhuguan_content(classId , count ,  selectedIndex , aflag , v);
                        System.out.println("?????????????????????_class_22222:  " + AnswerActivityTea.alist);
                    }
                }
            }
        });

        img_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                selectedIndex = 0;
                isSelect_huizong = true;
            }
        });
    }

    //?????????-????????????????????????
    private void showStudentsAnswer_img(View v){
        slStusAnswersImg = v.findViewById(R.id.slStusAnswersImg);
        slStusAnswers_zhuguan = v.findViewById(R.id.slStusAnswers);
        tx_noanswer_zhuguan = v.findViewById(R.id.tx_noanswer);
        slStusAnswersImg.setVisibility(View.VISIBLE);
        slStusAnswers_zhuguan.setVisibility(View.GONE);
        tx_noanswer_zhuguan.setVisibility(View.GONE);

        //????????????????????????????????????
        LinearLayout linearStusAnswersImg = v.findViewById(R.id.linearStusAnswersImg);
        linearStusAnswersImg.removeAllViews();

        int studentsNum = stusNameList.size(); //?????????????????????
        //????????????3??????????????????linearsNum???????????????????????????????????????????????????
        int linearsNum = (int)Math.ceil(studentsNum / 3.0);
        LinearLayout[] answersList = new LinearLayout[linearsNum];
        //???????????????LinearLayout????????????3??????
        for(int i = 0 ; i < answersList.length ; i++){
            answersList[i] = new LinearLayout(getActivity());
            //????????????
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            answersList[i].setWeightSum(3);  //3??????
            answersList[i].setLayoutParams(params);
            answersList[i].setOrientation(LinearLayout.HORIZONTAL);
        }

        if(screenWidth < 0){
            getScreenProps();
        }

        for(int i = 0 ; i < studentsNum ; i++){
            LinearLayout linearStu = new LinearLayout(getActivity());
            LinearLayout.LayoutParams linear_params = new LinearLayout.LayoutParams(0, (int)(screenHeight * 0.35), 1);
            //????????????3???????????????
            if(i % 3 == 0){
                linear_params.setMargins(10 , 3 , 5 , 3);
            }else{
                linear_params.setMargins(5 , 3 , 5 , 3);
            }
            linearStu.setOrientation(LinearLayout.VERTICAL);
            linearStu.setLayoutParams(linear_params);
            linearStu.setBackground(getResources().getDrawable(R.drawable.linear_shape));

            TextView tx_name = new TextView(getActivity());
            LinearLayout.LayoutParams tx_params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            tx_name.setLayoutParams(tx_params);
            tx_name.setPadding(0 , 2 , 0 , 2);
            tx_name.setGravity(Gravity.CENTER);
            tx_name.setText(stusNameList.get(i));
            tx_name.setTextColor(Color.parseColor("#FFFFFF"));

            linearStu.addView(tx_name);

            LinearLayout htmlView = new LinearLayout(getActivity());
            LinearLayout.LayoutParams htmlView_params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0,
                    1);
            htmlView_params.setMargins(2 , 0 , 2 , 2);
            htmlView.setLayoutParams(htmlView_params);
            htmlView.setOrientation(LinearLayout.VERTICAL);
            htmlView.setBackground(getResources().getDrawable(R.drawable.txt_shape));

            WebView answer_webView = new WebView(getActivity());
            answer_webView.setId(i);
            LinearLayout.LayoutParams webView_params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            //?????????????????????????????????
            webView_params.setMargins(5 , 0 , 5 , 5);
            answer_webView.setLayoutParams(webView_params);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //?????????????????????http???
                answer_webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }
            answer_webView.getSettings().setBlockNetworkImage(false); //??????????????????
            answer_webView.setHorizontalScrollBarEnabled(false); //????????????????????????
            answer_webView.setVerticalScrollBarEnabled(false);//????????????????????????
            answer_webView.getSettings().setDefaultTextEncodingName("UTF-8"); //??????????????????
            answer_webView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent ev) {
                    //ScrollView???webview????????????
                    //???????????????touch??????,????????????
//                    ((WebView)view).requestDisallowInterceptTouchEvent(true);
                    selectStuAnswerIndex = view.getId();
//                    Toast.makeText(getActivity(),"???????????????",Toast.LENGTH_SHORT).show();
                    showImageAnswer(v); //????????????????????????
                    return false;
                }
            });

            String unEncodedHtml = stusAnswerList.get(i).toString();
            answer_webView.loadDataWithBaseURL(null , unEncodedHtml , "text/html",  "utf-8", null);

            htmlView.addView(answer_webView);
            linearStu.addView(htmlView);
            answersList[i / 3].addView(linearStu);
        }
        for(int i = 0 ; i < answersList.length ; i++){
            linearStusAnswersImg.addView(answersList[i]);
        }
    }


    //?????????????????????-??????
    private void showImageAnswer(View v){
        LinearLayout linear1 = v.findViewById(R.id.linear1);
        LinearLayout linear_imgAndtxt = v.findViewById(R.id.linear_imgAndtxt);
        LinearLayout linear_img = v.findViewById(R.id.linear_img);
        //????????????
        linear_img.removeAllViews();

        ImageView img_back = v.findViewById(R.id.img_back);
        ImageView img_next = v.findViewById(R.id.img_next);
        TextView tx_who = v.findViewById(R.id.tx_who);
        TextView tx_num = v.findViewById(R.id.tx_num);
        TextView tx_close = v.findViewById(R.id.tx_close);

        linear1.setVisibility(View.GONE);
        linear_imgAndtxt.setVisibility(View.VISIBLE);

        Log.e("?????????????????????index???: ", selectStuAnswerIndex + "");
        if(selectStuAnswerIndex >= 0){
            Log.e("??????????????????????????????: ", stusNameList.get(selectStuAnswerIndex));
            Log.e("???????????????????????????url???: ", stusAnswerList.get(selectStuAnswerIndex));
            int allNum = stusAnswerList.size(); //????????????-?????????
            String tx = (selectStuAnswerIndex + 1) + "/" + allNum;
            tx_num.setText(tx);
            tx_who.setText(stusNameList.get(selectStuAnswerIndex)); //????????????
            WebView answer_webView = getWebView(); //????????????????????????
            linear_img.addView(answer_webView);
        }
        //?????????
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectStuAnswerIndex >= 1){
                    selectStuAnswerIndex--;
                    int allNum = stusAnswerList.size(); //????????????-?????????
                    String tx = (selectStuAnswerIndex + 1) + "/" + allNum;
                    tx_num.setText(tx);
                    tx_who.setText(stusNameList.get(selectStuAnswerIndex));
                    linear_img.removeAllViews();  //????????????
                    WebView answer_webView = getWebView();//????????????????????????
                    linear_img.addView(answer_webView);
                }
            }
        });
        //?????????
        img_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectStuAnswerIndex < stusAnswerList.size() - 1){
                    selectStuAnswerIndex++;
                    int allNum = stusAnswerList.size(); //????????????-?????????
                    String tx = (selectStuAnswerIndex + 1) + "/" + allNum;
                    tx_num.setText(tx);
                    tx_who.setText(stusNameList.get(selectStuAnswerIndex));
                    linear_img.removeAllViews();
                    WebView answer_webView = getWebView();//????????????????????????
//                    TextView answer_webView = getTextView();
                    linear_img.addView(answer_webView);
                }
            }
        });

        tx_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linear1.setVisibility(View.VISIBLE);
                linear_imgAndtxt.setVisibility(View.GONE);
                selectStuAnswerIndex = 0;
            }
        });
    }

    //??????textview
    private TextView getTextView(){
        TextView answer_webView = new TextView(getActivity());
        LinearLayout.LayoutParams webView_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        //?????????????????????????????????
        webView_params.setMargins(5 , 0 , 5 , 5);
        answer_webView.setLayoutParams(webView_params);
        final  String answer_stu = stusAnswerList.get(selectStuAnswerIndex).toString();
        MyImageGetter myImageGetter = new MyImageGetter(getActivity(), answer_webView);
//        MyTagHandler tagHandler = new MyTagHandler(getActivity());
        CharSequence sequence;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sequence = Html.fromHtml(answer_stu, Html.FROM_HTML_MODE_LEGACY, myImageGetter, null);
        } else {
            sequence = Html.fromHtml(answer_stu);
        }
        answer_webView.setText(sequence);

        answer_webView.setMovementMethod(ScrollingMovementMethod.getInstance());// ???????????????

        return answer_webView;
    }

    //??????webview
    private WebView getWebView(){
        WebView answer_webView = new WebView(getActivity());
        LinearLayout.LayoutParams webView_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        answer_webView.setLayoutParams(webView_params);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //?????????????????????http???
            answer_webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
//        answer_webView.getSettings().setJavaScriptEnabled(true);
//        answer_webView.getSettings().setDomStorageEnabled(true); //webView???????????????DOM Storage
        answer_webView.getSettings().setBlockNetworkImage(false); //??????????????????
        answer_webView.setHorizontalScrollBarEnabled(false);  //????????????????????????
        answer_webView.setVerticalScrollBarEnabled(false);//????????????????????????

        answer_webView.getSettings().setUseWideViewPort(true);//???????????? viewport
        answer_webView.getSettings().setLoadWithOverviewMode(true);   //???????????????  ????????????????????????
        answer_webView.getSettings().setBuiltInZoomControls(true);  //?????????????????????????????????
        answer_webView.getSettings().setDisplayZoomControls(false); //??????????????????
        answer_webView.getSettings().setSupportZoom(true);//??????????????????
        answer_webView.getSettings().setDefaultTextEncodingName("UTF-8");

        String unEncodedHtml = stusAnswerList.get(selectStuAnswerIndex).toString();
        answer_webView.loadDataWithBaseURL(null , unEncodedHtml , "text/html",  "utf-8", null);
        return answer_webView;
    }


    //?????????-????????????????????????
    private void showStudentsAnswer(View v){
        slStusAnswersImg = v.findViewById(R.id.slStusAnswersImg);
        slStusAnswers_zhuguan = v.findViewById(R.id.slStusAnswers);
        tx_noanswer_zhuguan = v.findViewById(R.id.tx_noanswer);
        slStusAnswers_zhuguan.setVisibility(View.VISIBLE);
        slStusAnswersImg.setVisibility(View.GONE);
        tx_noanswer_zhuguan.setVisibility(View.GONE);

        //?????????????????????????????????
        LinearLayout linearStusAnswers = v.findViewById(R.id.linearStusAnswers);
        linearStusAnswers.removeAllViews();
        //????????????
        for(int i = 0 ; i < stusAnswerList_answer_txt.size() ; i++){
            //???????????????????????????????????????????????????
            LinearLayout linearAnswer = new LinearLayout(getActivity());
            //????????????
            LinearLayout.LayoutParams linear_params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            linear_params.setMargins(20 , 0 , 20 , 5 );
            linearAnswer.setLayoutParams(linear_params);
            linearAnswer.setOrientation(LinearLayout.HORIZONTAL);

            TextView txt_da = new TextView(getActivity());
            //????????????
            LinearLayout.LayoutParams txt_params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            txt_da.setLayoutParams(txt_params);
//            txt_da.setTextSize(15);
            txt_da.setText("???:");
            txt_da.setTextColor(Color.parseColor("#828798"));

            TextView txt_answer = new TextView(getActivity());
            txt_answer.setPadding(10 , 0 , 0 , 0);
            txt_answer.setLayoutParams(txt_params);
//            txt_answer.setTextSize(15);
            txt_answer.setText(stusAnswerList_answer_txt.get(i));
            txt_answer.setTextColor(Color.parseColor("#33a3dc"));
            linearAnswer.addView(txt_da);
            linearAnswer.addView(txt_answer);
            linearStusAnswers.addView(linearAnswer);

            //???????????????????????????????????????????????????
            LinearLayout linearAll = new LinearLayout(getActivity());
            //????????????
            LinearLayout.LayoutParams linearAll_params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            linear_params.setMargins(10 , 0 , 10 , 0 );
            linearAll.setLayoutParams(linearAll_params);
            linearAll.setOrientation(LinearLayout.VERTICAL);

            int linearSum =  (int)Math.ceil(stusAnswerList_name_txt.get(i).size() / 8.0);
            LinearLayout[] answersList = new LinearLayout[linearSum];
            for(int k = 0 ; k < answersList.length ; k++){
                answersList[k] = new LinearLayout(getActivity());
                //????????????
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                answersList[k].setLayoutParams(params);
                answersList[k].setWeightSum(8);
                answersList[k].setOrientation(LinearLayout.HORIZONTAL);
            }
            for(int j = 0 ; j < stusAnswerList_name_txt.get(i).size() ; j++){
                TextView txt_name = new TextView(getActivity());
                LinearLayout.LayoutParams txtname_params = new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1);

                //????????????8???????????????
                if(j % 8 == 0){
                    txtname_params.setMargins(10 , 3 , 5 , 3);
                }else{
                    txtname_params.setMargins(5 , 3 , 5 , 3);
                }
                txt_name.setLayoutParams(txtname_params);
                txt_name.setPadding(5 , 2 , 5 , 2);
//                txt_name.setTextSize(15);
                txt_name.setGravity(Gravity.CENTER);
                txt_name.setText(stusAnswerList_name_txt.get(i).get(j));

                txt_name.setBackgroundColor(Color.parseColor("#d3d7d4"));
                txt_name.setTextColor(Color.parseColor("#828798"));
                answersList[j / 8].addView(txt_name);
            }
            for(int k = 0 ; k < answersList.length ; k++){
                linearAll.addView(answersList[k]);
            }
            linearStusAnswers.addView(linearAll);
        }

        //????????????
        if(stusAnswerList_name_img.size() > 0){
            TextView txt_paizhao = new TextView(getActivity());
            //????????????
            LinearLayout.LayoutParams txt_params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            txt_params.setMargins(10 , 5 , 10 , 5);
            txt_paizhao.setLayoutParams(txt_params);
//            txt_paizhao.setTextSize(15);
            txt_paizhao.setText("?????????");
            txt_paizhao.setTextColor(Color.parseColor("#828798"));
            linearStusAnswers.addView(txt_paizhao);

            //???????????????????????????????????????????????????
            LinearLayout linearAll = new LinearLayout(getActivity());
            //????????????
            LinearLayout.LayoutParams linear_params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            linearAll.setLayoutParams(linear_params);
            linearAll.setOrientation(LinearLayout.VERTICAL);

            int linearSum =  (int)Math.ceil(stusAnswerList_name_img.size() / 8.0);
            LinearLayout[] answersList = new LinearLayout[linearSum];
            for(int k = 0 ; k < answersList.length ; k++){
                answersList[k] = new LinearLayout(getActivity());
                //????????????
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                answersList[k].setLayoutParams(params);
                answersList[k].setWeightSum(8);
                answersList[k].setOrientation(LinearLayout.HORIZONTAL);
            }
            for(int i = 0 ; i < stusAnswerList_name_img.size() ; i++){
                TextView txt_name = new TextView(getActivity());
                LinearLayout.LayoutParams txtname_params = new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1);

                //????????????8???????????????
                if(i % 8 == 0){
                    txtname_params.setMargins(10 , 5 , 5 , 5);
                }else{
                    txtname_params.setMargins(5 , 5 , 5 , 5);
                }
                txt_name.setLayoutParams(txtname_params);
                txt_name.setPadding(5 , 2 , 5 , 2);
//                txt_name.setTextSize(15);
                txt_name.setGravity(Gravity.CENTER);
                txt_name.setText(stusAnswerList_name_img.get(i));

                txt_name.setBackgroundColor(Color.parseColor("#d3d7d4"));
                txt_name.setTextColor(Color.parseColor("#828798"));
                answersList[i / 8].addView(txt_name);
            }
            for(int k = 0 ; k < answersList.length ; k++){
                linearAll.addView(answersList[k]);
            }
            linearStusAnswers.addView(linearAll);
        }

        //?????????
//        if(stusAnswerList_Noanswer.size() > 0){
//            TextView txt_weida = new TextView(getActivity());
//            //????????????
//            LinearLayout.LayoutParams txt_params = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.WRAP_CONTENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT);
//            txt_params.setMargins(10 , 5 , 10 , 5);
//            txt_weida.setLayoutParams(txt_params);
//            txt_weida.setTextSize(15);
//            txt_weida.setText("?????????");
//            txt_weida.setTextColor(Color.parseColor("#828798"));
//            linearStusAnswers.addView(txt_weida);
//
//            //???????????????????????????????????????????????????
//            LinearLayout linearAll = new LinearLayout(getActivity());
//            //????????????
//            LinearLayout.LayoutParams linear_params = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT);
//            linearAll.setLayoutParams(linear_params);
//            linearAll.setOrientation(LinearLayout.VERTICAL);
//
//            int linearSum =  (int)Math.ceil(stusAnswerList_Noanswer.size() / 8.0);
//            LinearLayout[] answersList = new LinearLayout[linearSum];
//            for(int k = 0 ; k < answersList.length ; k++){
//                answersList[k] = new LinearLayout(getActivity());
//                //????????????
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.MATCH_PARENT,
//                        LinearLayout.LayoutParams.WRAP_CONTENT);
//                answersList[k].setLayoutParams(params);
//                answersList[k].setWeightSum(8);
//                answersList[k].setOrientation(LinearLayout.HORIZONTAL);
//            }
//            for(int i = 0 ; i < stusAnswerList_Noanswer.size() ; i++){
//                TextView txt_name = new TextView(getActivity());
//                LinearLayout.LayoutParams txtname_params = new LinearLayout.LayoutParams(
//                        0,
//                        LinearLayout.LayoutParams.WRAP_CONTENT,
//                        1);
//
//                //????????????8???????????????
//                if(i % 8 == 0){
//                    txtname_params.setMargins(10 , 5 , 5 , 5);
//                }else{
//                    txtname_params.setMargins(5 , 5 , 5 , 5);
//                }
//                txt_name.setLayoutParams(txtname_params);
//                txt_name.setPadding(5 , 2 , 5 , 2);
//                txt_name.setTextSize(15);
//                txt_name.setGravity(Gravity.CENTER);
//                txt_name.setText(stusAnswerList_Noanswer.get(i));
//
//                txt_name.setBackgroundColor(Color.parseColor("#d3d7d4"));
//                txt_name.setTextColor(Color.parseColor("#828798"));
//                answersList[i / 8].addView(txt_name);
//            }
//            for(int k = 0 ; k < answersList.length ; k++){
//                linearAll.addView(answersList[k]);
//            }
//            linearStusAnswers.addView(linearAll);
//        }
    }

    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }};
            SSLContext sc = SSLContext.getInstance("TLS");
            // trustAllCerts?????????????????????
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception ignored) {

        }
    }

    //??????800??????????????????????????????????????????????????????id
    private void getSuijiOrQiangdaStu(View  v){
        System.out.println("????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
        if(mTimer != null){
            mTimer.cancel();
            mTimer = null;
        }

        //??????delay???????????????????????????task???????????????period??????????????????task
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                //????????????????????????????????????!!!!!!????????????????????????????????????????????????
                getSelectedStu(v);
                //???????????????????????????????????????????????????????????????????????????
                if(stuName_selected != null && stuName_selected.length() > 0){
                    mTimer.cancel();
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() { //|| isClick_btClose == false
                            //???????????? ???????????? ??????
                            btEnd.setTextColor(Color.parseColor("#FFFFFFFF"));
                            btEnd.setBackground(getResources().getDrawable((R.drawable.btn_begin_enable)));

                            showSelectedStu(v);  //???????????????????????????
                            //??????????????????????????????????????????????????????????????????????????????????????????????????????,?????????????????????
                            if(txType_qiangda.isSelected()
                                    && stuName_selected != null
                                    && stuName_selected.length() > 0){
                                System.out.println("?????????????????????????????????????????????????????????????????????????????????????????????????????????" + stuName_selected);
                                beginOrEndQueAction("startAnswerQiangDa");
                            }
                            getSelectedStuAnswer(v); //?????????????????????????????????

                            tx_answers_sum.setVisibility(View.INVISIBLE);
                            jishiqi.setVisibility(View.VISIBLE);
                            jishiqi.setBase(SystemClock.elapsedRealtime());  //?????????????????? ???????????????0??????
                            jishiqi.start();   //????????????
                        }
                    });
                }
            }
        } , 800 , 800);
    }

    //????????????????????????????????????!!!!!!????????????????????????????????????????????????
    private void getSelectedStu(View v){
        new Thread(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(txType_tiwen.isSelected()){
                            txType = 1;
                        }else if(txType_suiji.isSelected()){
                            txType = 2;
                        }else{
                            txType = 3;
                        }
                    }
                });
                System.out.println("??????????????????????????????????????????????????????????????????: " + txType);
                AnswerActivityTea.stuId = "";
                AnswerActivityTea.stuName = "";
                Http_HuDongActivityTea.getSjOrQdStu(txType);   //?????????????????????????????????
                if(AnswerActivityTea.stuId != null && AnswerActivityTea.stuId.length() > 0){
                    suiji_qiangda_flag = 1; //?????????????????????
                    stuName_selected = AnswerActivityTea.stuName;
                    stuId_selected = AnswerActivityTea.stuId;
                    Log.e("??????????????????" , stuName_selected);
                    Log.e("????????????id" , stuId_selected);
                    System.out.println("?????????????????????????????????????????????????????????????????????????????????????????????" + stuName_selected + stuId_selected);
                }
            }
        }).start();
    }

    //??????800?????????????????????????????????????????????
    private void getSelectedStuAnswer(View v){
        if(mTimer != null){
            mTimer.cancel();
            mTimer = null;
        }

        //??????delay???????????????????????????task???????????????period??????????????????task
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
//                    Toast.makeText(getActivity(),"??????2",Toast.LENGTH_SHORT).show();
                //?????????????????????????????????????????????!!!!!!????????????????????????????????????????????????
                getStuAnswer();
                //?????????????????????
                if(stuAnswer_selected != null && stuAnswer_selected.length() > 0){
                    System.out.println("????????????222222????????????????????????????????????????????????????????????????????????" + stuAnswer_selected);
                    mTimer.cancel();
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {  //|| isClick_btClose == false
                            if (txModle_luru.isSelected()) { //????????????????????????????????????????????????????????????
                                btSingle.setTextColor(Color.parseColor("#FFFFFFFF"));
                                btAnswers.setTextColor(Color.parseColor("#80000000"));
                                btSingle.setBackground(getResources().getDrawable((R.drawable.btn_begin_enable)));
                                btAnswers.setBackground(getResources().getDrawable((R.drawable.btn_begin_unenable)));
                            } else { //????????????????????????????????????????????????
                                btSingle.setTextColor(Color.parseColor("#FFFFFFFF"));
                                btAnswers.setTextColor(Color.parseColor("#FFFFFFFF"));
                                btSingle.setBackground(getResources().getDrawable((R.drawable.btn_begin_enable)));
                                btAnswers.setBackground(getResources().getDrawable((R.drawable.btn_begin_enable)));
                            }
                            jishiqi.stop();   //????????????
                            //???????????????????????? ??? ??????????????????????????????
                            if(txModle_luru.isSelected()){
                                tx_tishi.setTextColor(Color.parseColor("#555D6D"));
                                tx_tishi.setText("????????????????????????");
                                //????????????????????????
                                View view_stuAnswer = View.inflate(getActivity() , R.layout.suiji_qiangda_stuanswer , null);
                                //????????????????????????????????????
                                showPopupWindow_suiji_qiangda_stuAnswer(view_stuAnswer);
                            }else{
                                tx_tishi.setTextColor(Color.parseColor("#ffe600"));
                                tx_tishi.setText(stuAnswer_selected);
                            }

                            if (answer_sq != null && answer_sq.length() > 0 && stuAnswer_selected.equals(answer_sq)) {
                                //??????????????????
                                img_zan.setVisibility(View.VISIBLE);
                            } else { //????????????????????????????????????
                                img_zan.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
            }
        } , 800 , 800);
    }


    //?????????????????????????????????
    private void getStuAnswer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                AnswerActivityTea.answer_sjOrQd = "";
                Http_HuDongActivityTea.getSjOrQdAnswer();
                if(AnswerActivityTea.answer_sjOrQd != null && AnswerActivityTea.answer_sjOrQd.length() > 0){
                    suiji_qiangda_flag = 2; //?????????????????????
                    stuAnswer_selected = AnswerActivityTea.answer_sjOrQd;
                    System.out.println("????????????11111????????????????????????????????????????????????????????????????????????" + stuAnswer_selected);
                }
            }
        }).start();
    }


    //???????????????????????????
    private void showSelectedStu(View view_selectStu){
        System.out.println("??????????????????????????????????????????????????????????????????????????????????????????");
        //Android????????????UI????????????UI
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                if(pw_selectStu != null){
                    System.out.println("??????????????????11111????????????????????????????????????????????????????????????????????????");
                    suiji_qiangda_flag = 1;
                    pw_selectStu.dismiss();
                }
                pw_selectStu = null;
                System.out.println("??????????????????222222????????????????????????????????????????????????????????????????????????");
                getScreenProps();
                //???popupWindow???????????????????????????view??????popupWindow???
                if(screenWidth <= 0){
                    getScreenProps();
                }
                pw_selectStu = new PopupWindow(view_selectStu , (int)(screenWidth * 0.17) , (int)(screenWidth * 0.17) , false);
//                pw_selectStu = new PopupWindow(view_selectStu , 200, 200, false);
                //???????????????????????????Gravity.CENTER,x???y????????????Gravity.CENTER?????????
//                pw_selectStu.showAtLocation(view_selectStu , Gravity.TOP | Gravity.RIGHT , (int)(screenWidth * 0.26) , (int)(screenHeight * 0.47));
                pw_selectStu.showAsDropDown(btSingle , -(pw_selectStu.getWidth() + 15) , -(pw_selectStu.getWidth() + 20));

                img_zan = view_selectStu.findViewById(R.id.img_zan);
                if(answer_sq != null && answer_sq.length() > 0 && stuAnswer_selected.equals(answer_sq)){
                    //??????????????????
                    img_zan.setVisibility(View.VISIBLE);
                }else{ //????????????????????????????????????
                    img_zan.setVisibility(View.INVISIBLE);
                }

                tx_stuname = view_selectStu.findViewById(R.id.tx_stu_name);
                tx_stuname.setText(stuName_selected);

                tx_tishi = view_selectStu.findViewById(R.id.tx_tishi);
                if(txModle_luru.isSelected()){
                    tx_tishi.setTextColor(Color.parseColor("#555D6D"));
                    if(suiji_qiangda_flag == 2 || stuAnswer_selected.length() > 0){
                        tx_tishi.setText("????????????????????????");
                    }else{
                        tx_tishi.setText("???????????????");
                    }
                }else{
                    if(suiji_qiangda_flag == 2 || stuAnswer_selected.length() > 0){
                        tx_tishi.setTextColor(Color.parseColor("#ffe600"));
                        tx_tishi.setText(stuAnswer_selected);
                    }else{
                        tx_tishi.setTextColor(Color.parseColor("#555D6D"));
                        tx_tishi.setText("???????????????");
                    }
                }


                tx_tishi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(txModle_luru.isSelected()){
                            if(suiji_qiangda_flag == 2 || stuAnswer_selected.length() > 0){
//                            Toast.makeText(getActivity(),"?????????????????????",Toast.LENGTH_SHORT).show();
                                View view_stuAnswer = View.inflate(getActivity() , R.layout.suiji_qiangda_stuanswer , null);
                                //????????????????????????????????????
                                showPopupWindow_suiji_qiangda_stuAnswer(view_stuAnswer);
                            }
                        }
                    }
                });
            }
        });
    }

    //????????????????????????????????????
    private void showPopupWindow_suiji_qiangda_stuAnswer(View v){
        if(pw_selectStuAnswer != null){
            suiji_qiangda_flag = 2;
            pw_selectStuAnswer.dismiss();
            pw_selectStuAnswer = null;
        }
        if(screenWidth <= 0){
            getScreenProps();
        }
        pw_selectStuAnswer = new PopupWindow(v , (int)(screenWidth * 0.7) , (int)(screenHeight * 0.8) , false);
        pw_selectStuAnswer.showAtLocation(v , Gravity.CENTER , 0 , 0);

        LinearLayout linear_webview = v.findViewById(R.id.linear_webview);
        linear_webview.removeAllViews();

        WebView answer_webView = new WebView(getActivity());
        LinearLayout.LayoutParams webView_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        answer_webView.setLayoutParams(webView_params);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //?????????????????????http???
            answer_webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        answer_webView.getSettings().setBlockNetworkImage(false); //??????????????????
        answer_webView.setHorizontalScrollBarEnabled(false);  //????????????????????????
        answer_webView.setVerticalScrollBarEnabled(false);//????????????????????????

        answer_webView.getSettings().setUseWideViewPort(true);//???????????? viewport
        answer_webView.getSettings().setLoadWithOverviewMode(true);   //???????????????  ????????????????????????
        answer_webView.getSettings().setBuiltInZoomControls(true);  //?????????????????????????????????
        answer_webView.getSettings().setDisplayZoomControls(false); //??????????????????
        answer_webView.getSettings().setSupportZoom(true);//??????????????????
        answer_webView.getSettings().setDefaultTextEncodingName("UTF-8");

        String unEncodedHtml = stuAnswer_selected.toString();
        answer_webView.loadDataWithBaseURL(null , unEncodedHtml , "text/html",  "utf-8", null);

        linear_webview.addView(answer_webView);

        TextView tx_who = v.findViewById(R.id.tx_who);
        tx_who.setText(stuName_selected);

        TextView tx_close = v.findViewById(R.id.tx_close);
        tx_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                suiji_qiangda_flag = 2;
                pw_selectStuAnswer.dismiss();
            }
        });
    }


    //??????????????????
    public void setDanxuanImgStatus_sq(){
        a_sq.setImageDrawable(getResources().getDrawable((R.mipmap.a)));
        b_sq.setImageDrawable(getResources().getDrawable((R.mipmap.b)));
        c_sq.setImageDrawable(getResources().getDrawable((R.mipmap.c)));
        d_sq.setImageDrawable(getResources().getDrawable((R.mipmap.d)));
        e_sq.setImageDrawable(getResources().getDrawable((R.mipmap.e)));
        f_sq.setImageDrawable(getResources().getDrawable((R.mipmap.f)));
        g_sq.setImageDrawable(getResources().getDrawable((R.mipmap.g)));
        h_sq.setImageDrawable(getResources().getDrawable((R.mipmap.h)));
        a_sq.setSelected(false);
        b_sq.setSelected(false);
        c_sq.setSelected(false);
        d_sq.setSelected(false);
        e_sq.setSelected(false);
        f_sq.setSelected(false);
        g_sq.setSelected(false);
        h_sq.setSelected(false);
    }


    //???????????????????????????
    private void showPopupWindow_suiji_qiangda_setAnswer(View v){
        if(pw_setAnswer != null){
            pw_setAnswer.dismiss();
            pw_setAnswer = null;
        }
        if(screenWidth <= 0){
            getScreenProps();
        }
        pw_setAnswer = new PopupWindow(v , (int)(screenWidth * 0.45) , (int)(screenHeight * 0.4) , false);
//        pw_setAnswer = new PopupWindow(v , 800 , 400 , false);
        pw_setAnswer.showAtLocation(v , Gravity.CENTER , 0 , 0);

        ImageView img_close = v.findViewById(R.id.imgClose);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pw_setAnswer.dismiss();
            }
        });

        //??????UI
        linear_danxuan_sq = v.findViewById(R.id.linearImg1);
        a_sq = v.findViewById(R.id.a);
        b_sq = v.findViewById(R.id.b);
        c_sq = v.findViewById(R.id.c);
        d_sq = v.findViewById(R.id.d);
        e_sq = v.findViewById(R.id.e);
        f_sq = v.findViewById(R.id.f);
        g_sq = v.findViewById(R.id.g);
        h_sq = v.findViewById(R.id.h);
        //??????UI
        linear_duoxuan_sq = v.findViewById(R.id.linearImg2);
        a1_sq = v.findViewById(R.id.a1);
        b1_sq = v.findViewById(R.id.b1);
        c1_sq = v.findViewById(R.id.c1);
        d1_sq = v.findViewById(R.id.d1);
        e1_sq = v.findViewById(R.id.e1);
        f1_sq = v.findViewById(R.id.f1);
        g1_sq = v.findViewById(R.id.g1);
        h1_sq = v.findViewById(R.id.h1);
        //??????UI
        linear_panduan_sq = v.findViewById(R.id.linearImg3);
        right_sq = v.findViewById(R.id.right);
        error_sq = v.findViewById(R.id.error);

        int count = chooseNum; //????????????
//        Toast.makeText(getActivity(),"???????????????" + count,Toast.LENGTH_SHORT).show();

        //2-4?????????
        LinearLayout.LayoutParams lps1 = new LinearLayout.LayoutParams(90, 90);
        lps1.setMargins(3 , 3 , 3 , 3);
        //5?????????
        LinearLayout.LayoutParams lps2 = new LinearLayout.LayoutParams(70, 70);
        lps2.setMargins(3 , 3 , 3 , 3);
        //6?????????
        LinearLayout.LayoutParams lps3 = new LinearLayout.LayoutParams(65, 65);
        lps3.setMargins(3 , 3 , 3 , 3);
        //7?????????
        LinearLayout.LayoutParams lps4 = new LinearLayout.LayoutParams(55, 55);
        lps4.setMargins(3 , 3 , 3 , 3);
        //8?????????
        LinearLayout.LayoutParams lps5 = new LinearLayout.LayoutParams(49, 49);
        lps5.setMargins(3 , 3 , 3 , 3);
        //??????(?????????
        if(txModle_danxuan.isSelected()){
            linear_danxuan_sq.setVisibility(View.VISIBLE);
            linear_duoxuan_sq.setVisibility(View.GONE);
            linear_panduan_sq.setVisibility(View.GONE);

            if(count == 2){
                a_sq.setLayoutParams(lps1);
                b_sq.setLayoutParams(lps1);
                c_sq.setVisibility(View.GONE);
                d_sq.setVisibility(View.GONE);
                e_sq.setVisibility(View.GONE);
                f_sq.setVisibility(View.GONE);
                g_sq.setVisibility(View.GONE);
                h_sq.setVisibility(View.GONE);
            }else if(count == 3){
                a_sq.setLayoutParams(lps1);
                b_sq.setLayoutParams(lps1);
                c_sq.setLayoutParams(lps1);
                d_sq.setVisibility(View.GONE);
                e_sq.setVisibility(View.GONE);
                f_sq.setVisibility(View.GONE);
                g_sq.setVisibility(View.GONE);
                h_sq.setVisibility(View.GONE);
            }else if(count == 4){
                a_sq.setLayoutParams(lps1);
                b_sq.setLayoutParams(lps1);
                c_sq.setLayoutParams(lps1);
                d_sq.setLayoutParams(lps1);
                e_sq.setVisibility(View.GONE);
                f_sq.setVisibility(View.GONE);
                g_sq.setVisibility(View.GONE);
                h_sq.setVisibility(View.GONE);
            }else if(count == 5){
                a_sq.setLayoutParams(lps2);
                b_sq.setLayoutParams(lps2);
                c_sq.setLayoutParams(lps2);
                d_sq.setLayoutParams(lps2);
                e_sq.setLayoutParams(lps2);
                f_sq.setVisibility(View.GONE);
                g_sq.setVisibility(View.GONE);
                h_sq.setVisibility(View.GONE);
            }else if(count == 6){
                a_sq.setLayoutParams(lps3);
                b_sq.setLayoutParams(lps3);
                c_sq.setLayoutParams(lps3);
                d_sq.setLayoutParams(lps3);
                e_sq.setLayoutParams(lps3);
                f_sq.setLayoutParams(lps3);
                g_sq.setVisibility(View.GONE);
                h_sq.setVisibility(View.GONE);
            }else if(count == 7){
                a_sq.setLayoutParams(lps4);
                b_sq.setLayoutParams(lps4);
                c_sq.setLayoutParams(lps4);
                d_sq.setLayoutParams(lps4);
                e_sq.setLayoutParams(lps4);
                f_sq.setLayoutParams(lps4);
                g_sq.setLayoutParams(lps4);
                h_sq.setVisibility(View.GONE);
            }else if(count == 8){
                a_sq.setLayoutParams(lps5);
                b_sq.setLayoutParams(lps5);
                c_sq.setLayoutParams(lps5);
                d_sq.setLayoutParams(lps5);
                e_sq.setLayoutParams(lps5);
                f_sq.setLayoutParams(lps5);
                g_sq.setLayoutParams(lps5);
                h_sq.setLayoutParams(lps5);
            }
        }
        //??????(?????????
        if(txModle_duoxuan.isSelected()){
            linear_danxuan_sq.setVisibility(View.GONE);
            linear_panduan_sq.setVisibility(View.GONE);
            linear_duoxuan_sq.setVisibility(View.VISIBLE);
            if(count == 2){
                a1_sq.setLayoutParams(lps1);
                b1_sq.setLayoutParams(lps1);
                c1_sq.setVisibility(View.GONE);
                d1_sq.setVisibility(View.GONE);
                e1_sq.setVisibility(View.GONE);
                f1_sq.setVisibility(View.GONE);
                g1_sq.setVisibility(View.GONE);
                h1_sq.setVisibility(View.GONE);
            }else if(count == 3){
                a1_sq.setLayoutParams(lps1);
                b1_sq.setLayoutParams(lps1);
                c1_sq.setLayoutParams(lps1);
                d1_sq.setVisibility(View.GONE);
                e1_sq.setVisibility(View.GONE);
                f1_sq.setVisibility(View.GONE);
                g1_sq.setVisibility(View.GONE);
                h1_sq.setVisibility(View.GONE);
            }else if(count == 4){
                a1_sq.setLayoutParams(lps1);
                b1_sq.setLayoutParams(lps1);
                c1_sq.setLayoutParams(lps1);
                d1_sq.setLayoutParams(lps1);
                e1_sq.setVisibility(View.GONE);
                f1_sq.setVisibility(View.GONE);
                g1_sq.setVisibility(View.GONE);
                h1_sq.setVisibility(View.GONE);
            }else if(count == 5){
                a1_sq.setLayoutParams(lps2);
                b1_sq.setLayoutParams(lps2);
                c1_sq.setLayoutParams(lps2);
                d1_sq.setLayoutParams(lps2);
                e1_sq.setLayoutParams(lps2);
                f1_sq.setVisibility(View.GONE);
                g1_sq.setVisibility(View.GONE);
                h1_sq.setVisibility(View.GONE);
            }else if(count == 6){
                a1_sq.setLayoutParams(lps3);
                b1_sq.setLayoutParams(lps3);
                c1_sq.setLayoutParams(lps3);
                d1_sq.setLayoutParams(lps3);
                e1_sq.setLayoutParams(lps3);
                f1_sq.setLayoutParams(lps3);
                g1_sq.setVisibility(View.GONE);
                h1_sq.setVisibility(View.GONE);
            }else if(count == 7){
                a1_sq.setLayoutParams(lps4);
                b1_sq.setLayoutParams(lps4);
                c1_sq.setLayoutParams(lps4);
                d1_sq.setLayoutParams(lps4);
                e1_sq.setLayoutParams(lps4);
                f1_sq.setLayoutParams(lps4);
                g1_sq.setLayoutParams(lps4);
                h1_sq.setVisibility(View.GONE);
            }else if(count == 8){
                a1_sq.setLayoutParams(lps5);
                b1_sq.setLayoutParams(lps5);
                c1_sq.setLayoutParams(lps5);
                d1_sq.setLayoutParams(lps5);
                e1_sq.setLayoutParams(lps5);
                f1_sq.setLayoutParams(lps5);
                g1_sq.setLayoutParams(lps5);
                h1_sq.setLayoutParams(lps5);
            }
        }
        //??????(?????????
        if(txModle_panduan.isSelected()){
            linear_danxuan_sq.setVisibility(View.GONE);
            linear_duoxuan_sq.setVisibility(View.GONE);
            linear_panduan_sq.setVisibility(View.VISIBLE);
        }

        //????????????????????????
        a_sq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDanxuanImgStatus_sq();
                a_sq.setSelected(true);
                a_sq.setImageDrawable(getResources().getDrawable((R.mipmap.a_select)));
            }
        });
        b_sq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDanxuanImgStatus_sq();
                b_sq.setSelected(true);
                b_sq.setImageDrawable(getResources().getDrawable((R.mipmap.b_select)));
            }
        });
        c_sq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDanxuanImgStatus_sq();
                c_sq.setSelected(true);
                c_sq.setImageDrawable(getResources().getDrawable((R.mipmap.c_select)));
            }
        });
        d_sq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDanxuanImgStatus_sq();
                d_sq.setSelected(true);
                d_sq.setImageDrawable(getResources().getDrawable((R.mipmap.d_select)));
            }
        });
        e_sq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDanxuanImgStatus_sq();
                e_sq.setSelected(true);
                e_sq.setImageDrawable(getResources().getDrawable((R.mipmap.e_select)));
            }
        });
        f_sq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDanxuanImgStatus_sq();
                f_sq.setSelected(true);
                f_sq.setImageDrawable(getResources().getDrawable((R.mipmap.f_select)));
            }
        });
        g_sq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDanxuanImgStatus_sq();
                g_sq.setSelected(true);
                g_sq.setImageDrawable(getResources().getDrawable((R.mipmap.g_select)));
            }
        });
        h_sq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDanxuanImgStatus_sq();
                h_sq.setSelected(true);
                h_sq.setImageDrawable(getResources().getDrawable((R.mipmap.h_select)));
            }
        });
        //????????????????????????
        a1_sq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //????????????imageView???????????????????????????????????????imageview??????????????????
                if((a1_sq.getDrawable().getCurrent().getConstantState()).equals(
                        ContextCompat.getDrawable(getActivity(), R.mipmap.ad_select).getConstantState())
                ) {
                    a1_sq.setSelected(false);
                    a1_sq.setImageDrawable(getResources().getDrawable((R.mipmap.ad)));
                }else {
                    a1_sq.setSelected(true);
                    a1_sq.setImageDrawable(getResources().getDrawable((R.mipmap.ad_select)));
                }

            }
        });
        b1_sq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((b1_sq.getDrawable().getCurrent().getConstantState()).equals(ContextCompat.getDrawable(getActivity(), R.mipmap.bd_select).getConstantState())){
                    b1_sq.setSelected(false);
                    b1_sq.setImageDrawable(getResources().getDrawable((R.mipmap.bd)));
                }else{
                    b1_sq.setSelected(true);
                    b1_sq.setImageDrawable(getResources().getDrawable((R.mipmap.bd_select)));
                }
            }
        });
        c1_sq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((c1_sq.getDrawable().getCurrent().getConstantState()).equals(ContextCompat.getDrawable(getActivity(), R.mipmap.cd_select).getConstantState())){
                    c1_sq.setSelected(false);
                    c1_sq.setImageDrawable(getResources().getDrawable((R.mipmap.cd)));
                }else{
                    c1_sq.setSelected(true);
                    c1_sq.setImageDrawable(getResources().getDrawable((R.mipmap.cd_select)));
                }
            }
        });
        d1_sq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((d1_sq.getDrawable().getCurrent().getConstantState()).equals(ContextCompat.getDrawable(getActivity(), R.mipmap.dd_select).getConstantState())){
                    d1_sq.setSelected(false);
                    d1_sq.setImageDrawable(getResources().getDrawable((R.mipmap.dd)));
                }else{
                    d1_sq.setSelected(true);
                    d1_sq.setImageDrawable(getResources().getDrawable((R.mipmap.dd_select)));
                }
            }
        });
        e1_sq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((e1_sq.getDrawable().getCurrent().getConstantState()).equals(ContextCompat.getDrawable(getActivity(), R.mipmap.ed_select).getConstantState())){
                    e1_sq.setSelected(false);
                    e1_sq.setImageDrawable(getResources().getDrawable((R.mipmap.ed)));
                }else{
                    e1_sq.setSelected(true);
                    e1_sq.setImageDrawable(getResources().getDrawable((R.mipmap.ed_select)));
                }
            }
        });
        f1_sq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((f1_sq.getDrawable().getCurrent().getConstantState()).equals(ContextCompat.getDrawable(getActivity(), R.mipmap.fd_select).getConstantState())){
                    f1_sq.setSelected(false);
                    f1_sq.setImageDrawable(getResources().getDrawable((R.mipmap.fd)));
                }else{
                    f1_sq.setSelected(true);
                    f1_sq.setImageDrawable(getResources().getDrawable((R.mipmap.fd_select)));
                }
            }
        });
        g1_sq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((g1_sq.getDrawable().getCurrent().getConstantState()).equals(ContextCompat.getDrawable(getActivity(), R.mipmap.gd_select).getConstantState())){
                    g1_sq.setSelected(false);
                    g1_sq.setImageDrawable(getResources().getDrawable((R.mipmap.gd)));
                }else{
                    g1_sq.setSelected(true);
                    g1_sq.setImageDrawable(getResources().getDrawable((R.mipmap.gd_select)));
                }
            }
        });
        h1_sq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((h1_sq.getDrawable().getCurrent().getConstantState()).equals(ContextCompat.getDrawable(getActivity(), R.mipmap.hd_select).getConstantState())){
                    h1_sq.setSelected(false);
                    h1_sq.setImageDrawable(getResources().getDrawable((R.mipmap.hd)));
                }else{
                    h1_sq.setSelected(true);
                    h1_sq.setImageDrawable(getResources().getDrawable((R.mipmap.hd_select)));
                }
            }
        });
        //????????????????????????
        right_sq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                right_sq.setSelected(true);
                error_sq.setSelected(false);
                right_sq.setImageDrawable(getResources().getDrawable((R.mipmap.right_select)));
                error_sq.setImageDrawable(getResources().getDrawable((R.mipmap.error)));
            }
        });
        error_sq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                right_sq.setSelected(false);
                error_sq.setSelected(true);
                right_sq.setImageDrawable(getResources().getDrawable((R.mipmap.right)));
                error_sq.setImageDrawable(getResources().getDrawable((R.mipmap.error_select)));
            }
        });

        Button bt_save = v.findViewById(R.id.btSave);
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txModle_danxuan.isSelected()){
                    answer_sq = "";
                    if(a_sq.isSelected()){
                        answer_sq = "A";
                    }else if(b_sq.isSelected()){
                        answer_sq = "B";
                    }else if(c_sq.isSelected()){
                        answer_sq = "C";
                    }else if(d_sq.isSelected()){
                        answer_sq = "D";
                    }else if(e_sq.isSelected()){
                        answer_sq = "E";
                    }else if(f_sq.isSelected()){
                        answer_sq = "F";
                    }else if(g_sq.isSelected()){
                        answer_sq = "G";
                    }else if(h_sq.isSelected()){
                        answer_sq = "H";
                    }else{
                        answer_sq = "";
                    }
//                    Toast.makeText(getActivity(),"??????????????????" + answer_sq,Toast.LENGTH_SHORT).show();
                }else if(txModle_duoxuan.isSelected()){
                    answer_sq = "";
                    if(a1_sq.isSelected()){ answer_sq = answer_sq + "A"; }
                    if(b1_sq.isSelected()){ answer_sq = answer_sq + "B"; }
                    if(c1_sq.isSelected()){ answer_sq = answer_sq + "C"; }
                    if(d1_sq.isSelected()){ answer_sq = answer_sq + "D"; }
                    if(e1_sq.isSelected()){ answer_sq = answer_sq + "E"; }
                    if(f1_sq.isSelected()){ answer_sq = answer_sq + "F"; }
                    if(g1_sq.isSelected()){ answer_sq = answer_sq + "G"; }
                    if(h1_sq.isSelected()){ answer_sq = answer_sq + "H"; }
//                    Toast.makeText(getActivity(),"??????????????????" + answer_sq,Toast.LENGTH_SHORT).show();
                }else if(txModle_panduan.isSelected()){
                    answer_sq = "";
                    if(right_sq.isSelected()){
                        answer_sq = "???";
                    }else if(error_sq.isSelected()){
                        answer_sq = "???";
                    }else{
                        answer_sq = "";
                    }
//                    Toast.makeText(getActivity(),"??????????????????" + answer_sq,Toast.LENGTH_SHORT).show();
                }

                if(answer_sq.length() > 0){
                    if((txModle_duoxuan.isSelected() && answer_sq.length() >= 2)
                            || txModle_panduan.isSelected()
                            || txModle_danxuan.isSelected()
                    ){
                        setAnswer_sq(AnswerActivityTea.currentketangId);
                        //??????????????????wait 500ms
                        synchronized (Thread.currentThread()){
                            try {
                                Thread.currentThread().wait(500);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                        System.out.println("???????????????????????????????????????");
                        if(setAnswer_status_sq){
                            Toast.makeText(getActivity(),"??????????????????",Toast.LENGTH_SHORT).show();
                            suiji_qiangda_flag = 3;
                            pw_setAnswer.dismiss(); //?????????????????????????????????popupwindow
                            //?????????????????????????????????????????????????????????????????????
                            if(answer_sq.equals(stuAnswer_selected)){
                                img_zan.setVisibility(View.VISIBLE);
                            }else{
                                img_zan.setVisibility(View.INVISIBLE);
                            }
                            Toast.makeText(getActivity(),"?????????" + answer_sq ,Toast.LENGTH_SHORT).show();
                        }else{
                            answer_sq = "";
                            Toast.makeText(getActivity(),"??????????????????",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getActivity(),"???????????????",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(),"???????????????",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //??????????????????????????????????????????
    private void setAnswer_sq(String ketangId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                setAnswer_status_sq = Http_HuDongActivityTea.setAnswer(ketangId , answer_sq);
            }
        }).start();
    }

    //?????????????????????
    private void showChooseNum_pw(View v){
        View chooseNum_list = View.inflate(getActivity() , R.layout.choosenum_list , null);
        pw_chooseNum = new PopupWindow(chooseNum_list , v.getWidth() , v.getHeight() * 7 , true);
        pw_chooseNum.showAsDropDown(v , 0 , 0);

        tx_2 = chooseNum_list.findViewById(R.id.tx_2);
        tx_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChooseStatus();
                tx_2.setBackgroundColor(Color.parseColor("#FFBB86FC"));
                tx_choosenum.setText("2");
                chooseNum = 2;
//                Toast.makeText(getActivity(),"????????????" + chooseNum,Toast.LENGTH_SHORT).show();
                pw_chooseNum.dismiss();
            }
        });
        tx_3 = chooseNum_list.findViewById(R.id.tx_3);
        tx_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChooseStatus();
                tx_3.setBackgroundColor(Color.parseColor("#FFBB86FC"));
                chooseNum = 3;
//                Toast.makeText(getActivity(),"????????????" + chooseNum,Toast.LENGTH_SHORT).show();
                tx_choosenum.setText("3");
                pw_chooseNum.dismiss();
            }
        });
        tx_4 = chooseNum_list.findViewById(R.id.tx_4);
        tx_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChooseStatus();
                tx_4.setBackgroundColor(Color.parseColor("#FFBB86FC"));
                chooseNum = 4;
//                Toast.makeText(getActivity(),"????????????" + chooseNum,Toast.LENGTH_SHORT).show();
                tx_choosenum.setText("4");
                pw_chooseNum.dismiss();
            }
        });
        tx_5 = chooseNum_list.findViewById(R.id.tx_5);
        tx_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChooseStatus();
                tx_5.setBackgroundColor(Color.parseColor("#FFBB86FC"));
                tx_choosenum.setText("5");
                chooseNum = 5;
//                Toast.makeText(getActivity(),"????????????" + chooseNum,Toast.LENGTH_SHORT).show();
                pw_chooseNum.dismiss();
            }
        });
        tx_6 = chooseNum_list.findViewById(R.id.tx_6);
        tx_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChooseStatus();
                tx_6.setBackgroundColor(Color.parseColor("#FFBB86FC"));
                tx_choosenum.setText("6");
                chooseNum = 6;
//                Toast.makeText(getActivity(),"????????????" + chooseNum,Toast.LENGTH_SHORT).show();
                pw_chooseNum.dismiss();
            }
        });
        tx_7 = chooseNum_list.findViewById(R.id.tx_7);
        tx_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChooseStatus();
                tx_7.setBackgroundColor(Color.parseColor("#FFBB86FC"));
                tx_choosenum.setText("7");
                chooseNum = 7;
//                Toast.makeText(getActivity(),"????????????" + chooseNum,Toast.LENGTH_SHORT).show();
                pw_chooseNum.dismiss();
            }
        });
        tx_8 = chooseNum_list.findViewById(R.id.tx_8);
        tx_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChooseStatus();
                tx_8.setBackgroundColor(Color.parseColor("#FFBB86FC"));
                tx_choosenum.setText("8");
                chooseNum = 8;
//                Toast.makeText(getActivity(),"????????????" + chooseNum,Toast.LENGTH_SHORT).show();
                pw_chooseNum.dismiss();
            }
        });

        setChooseStatus();
        if(chooseNum == 2){
            tx_2.setBackgroundColor(Color.parseColor("#FFBB86FC"));
        }else if(chooseNum == 3){
            tx_3.setBackgroundColor(Color.parseColor("#FFBB86FC"));
        }else if(chooseNum == 4){
            tx_4.setBackgroundColor(Color.parseColor("#FFBB86FC"));
        }else if(chooseNum == 5){
            tx_5.setBackgroundColor(Color.parseColor("#FFBB86FC"));
        }else if(chooseNum == 6){
            tx_6.setBackgroundColor(Color.parseColor("#FFBB86FC"));
        }else if(chooseNum == 7){
            tx_7.setBackgroundColor(Color.parseColor("#FFBB86FC"));
        }else if(chooseNum == 8){
            tx_8.setBackgroundColor(Color.parseColor("#FFBB86FC"));
        }
    }

    //???????????????item??????
    private void setChooseStatus(){
        tx_2.setBackgroundColor(Color.parseColor("#FFFFFF"));
        tx_3.setBackgroundColor(Color.parseColor("#FFFFFF"));
        tx_4.setBackgroundColor(Color.parseColor("#FFFFFF"));
        tx_5.setBackgroundColor(Color.parseColor("#FFFFFF"));
        tx_6.setBackgroundColor(Color.parseColor("#FFFFFF"));
        tx_7.setBackgroundColor(Color.parseColor("#FFFFFF"));
        tx_8.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }


    //???????????????1s???????????????????????????
    private void getSubmitAnswerStuNum(){
        if(mTimer_stuNum != null){
            mTimer_stuNum.cancel();
            mTimer_stuNum = null;
        }

        //??????delay???????????????????????????task???????????????period??????????????????task
        mTimer_stuNum = new Timer();
        mTimer_stuNum.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("???????????????????????????????????????????????????????????????????????????????????????????????????");
                Http_HuDongActivityTea.getSubmitAnswerStuNum(); //?????????
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        int stuNum = AnswerActivityTea.answerNum;
                        initJoinClassInformation();
                        int allNum = getJoinClassAllStuNum();
                        tx_answers_sum.setText(stuNum + "/" + allNum + "??????????????????");
                    }
                });
            }
        } , 1000 , 1000);
    }

    //???????????????????????????uuid???answerQuestionId?????????????????????????????????
    private void initData(){
        chooseNum = Integer.valueOf((String) tx_choosenum.getText());
        suiji_qiangda_flag = 0;
        if(mTimer != null){
            mTimer.cancel();
            mTimer = null;
        }else{
            mTimer = null;
        }
        if(mTimer_stuNum != null){
            mTimer_stuNum.cancel();
            mTimer_stuNum = null;
        }else{
            mTimer_stuNum = null;
        }
        answer = "";
        setAnswer_status = false;
        answer_sq = "";
        setAnswer_status_sq = false;
        isSelect_huizong = true;
        selectedIndex = 0;
        selectStuAnswerIndex = 0;
        stuName_selected = "";
        stuId_selected = "";
        stuAnswer_selected = "";
    }

    //????????????2 ??????????????????????????????
    private void beginOrEndQueAction(String actionName){
        System.out.println("??????????????????2??????????????????!!!!!!!!!!!: " + actionName);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(actionName.indexOf("start") >= 0 && !actionName.equals("startAnswerQiangDa")){
                    getUUID();//??????uuid
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MainActivity_tea activity = (MainActivity_tea)getActivity();
                            activity.ScreenShotBoard(getContext(), AnswerActivityTea.answerQuestionId,activity.getmBoard());
                        }
                    });
                }
                int questionAnswerType = 0;  //????????????
                if(txType_tiwen.isSelected()){
                    questionAnswerType = 1;
                }else if(txType_suiji.isSelected()){
                    questionAnswerType = 2;
                }else if(txType_qiangda.isSelected()){
                    questionAnswerType = 3;
                }

                int questionSubNum = chooseNum; //????????????

                int questionBaseTypeId = 101; //???????????????
                if(txModle_danxuan.isSelected()){
                    questionBaseTypeId = 101;
                }else if(txModle_duoxuan.isSelected()){
                    questionBaseTypeId = 102;
                }else if(txModle_panduan.isSelected()){
                    questionBaseTypeId = 103;
                }else if(txModle_luru.isSelected()){
                    questionBaseTypeId = 104;
                }

                String questionAction = actionName; //????????????
                //????????????2
                AnswerActivityTea.isSuccess = Http_HuDongActivityTea.saveQueHdAction(
                        questionAnswerType ,
                        questionSubNum ,
                        questionBaseTypeId ,
                        questionAction
                );
            }
        }).start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        handleSSLHandshake();

        // ??????????????????
        View contentView = inflater.inflate(R.layout.answer_question, container, false);
        FcontentView = contentView;
        txType_tiwen = (TextView) contentView.findViewById(R.id.txt_tiwen);
        txType_suiji = (TextView) contentView.findViewById(R.id.txt_suiji);
        txType_qiangda = (TextView) contentView.findViewById(R.id.txt_qiangda);

        img_tiwen = (ImageView) contentView.findViewById(R.id.img_tiwen);
        img_suiji = (ImageView) contentView.findViewById(R.id.img_suiji);
        img_qiangda = (ImageView) contentView.findViewById(R.id.img_qiangda);

        txModle_danxuan = (TextView) contentView.findViewById(R.id.txt_danxuan);
        txModle_duoxuan = (TextView) contentView.findViewById(R.id.txt_duoxuan);
        txModle_panduan = (TextView) contentView.findViewById(R.id.txt_panduan);
        txModle_luru = (TextView) contentView.findViewById(R.id.txt_luru);

        imgdanxuan = (ImageView) contentView.findViewById(R.id.imgdanxuan);
        imgduoxuan = (ImageView) contentView.findViewById(R.id.imgduoxuan);
        imgpanduan = (ImageView) contentView.findViewById(R.id.imgpanduan);
        imgluru = (ImageView) contentView.findViewById(R.id.imgluru);

//        spinner = (TextView) contentView.findViewById(R.id.tx_choosenum);
        linear_choose = (LinearLayout) contentView.findViewById(R.id.linear_choose);
        txChoose = (TextView) contentView.findViewById(R.id.txChoose);
        tx_choosenum = (TextView) contentView.findViewById(R.id.tx_choosenum);
        img_choose = (ImageView) contentView.findViewById(R.id.img_choose);

        linear_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //                Toast.makeText(getActivity(),"??????????????????",Toast.LENGTH_SHORT).show();
                if(pw_chooseNum != null && pw_chooseNum.isShowing()){
                    pw_chooseNum.dismiss();
                }else{
                    showChooseNum_pw(view);
                }
            }
        });

//        img_choose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Toast.makeText(getActivity(),"??????????????????",Toast.LENGTH_SHORT).show();
//                if(pw_chooseNum != null && pw_chooseNum.isShowing()){
//                    pw_chooseNum.dismiss();
//                }else{
//                    showChooseNum_pw(view);
//                }
//            }
//        });

        jishiqi = (Chronometer) contentView.findViewById(R.id.id_jishiqi);
        tx_answers_sum = (TextView) contentView.findViewById(R.id.tx_answers_sum);

        btBegin1 = (Button) contentView.findViewById(R.id.btBegin1);
        btBegin2 = (Button) contentView.findViewById(R.id.btBegin2);

        btBegin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();
//                answer = "";
//                answer_sq = "";
//                chooseNum = Integer.valueOf((String) tx_choosenum.getText());
                btBegin2.setVisibility(View.GONE);
                linear1.setVisibility(View.VISIBLE);
                linear2.setVisibility(View.VISIBLE);
                if(txType_tiwen.isSelected()){
                    if(txModle_luru.isSelected() || imgluru.isSelected()){
                        btSingle.setText("????????????");
                    }else{
                        btSingle.setText("????????????");
                    }
                    btAnswers.setText("????????????");
                    btEnd.setText("????????????");
                    btClosed.setText("????????????");

                    btSingle.setTextColor(Color.parseColor("#FFFFFFFF"));
                    btAnswers.setTextColor(Color.parseColor("#FFFFFFFF"));
                    btEnd.setTextColor(Color.parseColor("#FFFFFFFF"));
                    btSingle.setBackground(getResources().getDrawable((R.drawable.btn_begin_enable)));
                    btAnswers.setBackground(getResources().getDrawable((R.drawable.btn_begin_enable)));
                    btEnd.setBackground(getResources().getDrawable((R.drawable.btn_begin_enable)));

                    tx_answers_sum.setVisibility(View.VISIBLE);
                    tx_answers_sum.setText("0/60??????????????????");
                    jishiqi.setVisibility(View.VISIBLE);
                    jishiqi.setBase(SystemClock.elapsedRealtime());  //?????????????????? ???????????????0??????
                    jishiqi.start();   //????????????

                    //????????????????????????????????????????????????
                    beginOrEndQueAction("startAnswer");

                    //???????????????????????????1s???????????????????????????
                    getSubmitAnswerStuNum();
                }else if(txType_suiji.isSelected()){
                    //????????????????????????????????????????????????
                    beginOrEndQueAction("startAnswerSuiji");

                    btSingle.setText("??????");
                    btAnswers.setText("????????????");
                    btEnd.setText("????????????");
                    btClosed.setText("????????????");
                    tx_answers_sum.setVisibility(View.VISIBLE);
                    tx_answers_sum.setText("?????????????????????");
                    jishiqi.setVisibility(View.INVISIBLE);
                    //?????????????????????????????????????????????
                    btSingle.setTextColor(Color.parseColor("#80000000"));
                    btAnswers.setTextColor(Color.parseColor("#80000000"));
                    btEnd.setTextColor(Color.parseColor("#80000000"));
                    btSingle.setBackground(getResources().getDrawable((R.drawable.btn_begin_unenable)));
                    btAnswers.setBackground(getResources().getDrawable((R.drawable.btn_begin_unenable)));
                    btEnd.setBackground(getResources().getDrawable((R.drawable.btn_begin_unenable)));

                    View view_selectStu = LayoutInflater.from(getActivity()).inflate(R.layout.suiji_qiangda_selectstu, null, false);
                    //?????????????????????????????????/??????????????????
                    getSuijiOrQiangdaStu(view_selectStu);
                }else if(txType_qiangda.isSelected()){
                    //??????????????????????????????????????????
                    beginOrEndQueAction("startQiangDa");

                    btSingle.setText("??????");
                    btAnswers.setText("????????????");
                    btEnd.setText("????????????");
                    btClosed.setText("????????????");
                    tx_answers_sum.setVisibility(View.VISIBLE);
                    tx_answers_sum.setText("?????????????????????");
                    jishiqi.setVisibility(View.INVISIBLE);
                    //?????????????????????????????????????????????
                    btSingle.setTextColor(Color.parseColor("#80000000"));
                    btAnswers.setTextColor(Color.parseColor("#80000000"));
                    btEnd.setTextColor(Color.parseColor("#80000000"));
                    btSingle.setBackground(getResources().getDrawable((R.drawable.btn_begin_unenable)));
                    btAnswers.setBackground(getResources().getDrawable((R.drawable.btn_begin_unenable)));
                    btEnd.setBackground(getResources().getDrawable((R.drawable.btn_begin_unenable)));

//                    isClick_btClose = false;
//                    Toast.makeText(getActivity(),"????????????",Toast.LENGTH_SHORT).show();
                    View view_selectStu = LayoutInflater.from(getActivity()).inflate(R.layout.suiji_qiangda_selectstu, null, false);
                    //?????????????????????????????????/??????????????????
                    getSuijiOrQiangdaStu(view_selectStu);
                }
            }
        });

        linear1 = (LinearLayout) contentView.findViewById(R.id.linear1);
        linear2 = (LinearLayout) contentView.findViewById(R.id.linear2);

        btSingle = (Button) contentView.findViewById(R.id.btSingle);
        btAnswers = (Button) contentView.findViewById(R.id.btAnswers);
        btEnd = (Button) contentView.findViewById(R.id.btEnd);
        btClosed = (Button) contentView.findViewById(R.id.btClosed);

        //?????????????????????????????????????????????????????????
        btSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                View view = inflater.inflate(R.layout.single_question_analysis , null , false);
                if(txType_tiwen.isSelected()){
                    if(!imgluru.isSelected()){ //????????????????????????????????????
                        View view = LayoutInflater.from(getActivity()).inflate(R.layout.single_question_analysis, null, false);
                        showPopupWindow(view , 1);
                    }else{ //??????????????????
                        View view = LayoutInflater.from(getActivity()).inflate(R.layout.luru_question_analysis, null, false);
                        showPopupWindow_luru(view , 1);
                    }
                }else{
                    //???????????????????????????????????????????????????
                    if(stuAnswer_selected.length() > 0) {
                        //???????????????
                        if (txModle_luru.isSelected()) {
                            img_zan.setVisibility(View.VISIBLE); //??????????????????
                        } else {//???????????????
                            answer_sq = stuAnswer_selected;
                            //?????????????????????????????????????????????????????????????????????
                            setAnswer_sq(AnswerActivityTea.currentketangId);
                            //??????????????????wait 500ms
                            synchronized (Thread.currentThread()){
                                try {
                                    Thread.currentThread().wait(500);
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                            }
                            System.out.println("???????????????????????????????????????");
                            if(setAnswer_status_sq){
                                Toast.makeText(getActivity(),"??????????????????",Toast.LENGTH_SHORT).show();
                                suiji_qiangda_flag = 3;
                                if(answer_sq.equals(stuAnswer_selected)){
                                    img_zan.setVisibility(View.VISIBLE);
                                }else{
                                    img_zan.setVisibility(View.INVISIBLE);
                                }
                                Toast.makeText(getActivity(),"?????????" + answer_sq ,Toast.LENGTH_SHORT).show();
                            }else{
                                answer_sq = "";
                                Toast.makeText(getActivity(),"??????????????????",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else{
                        Toast.makeText(getActivity(),"????????????????????????",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        //??????????????????????????????
        btAnswers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                View view = inflater.inflate(R.layout.single_question_analysis , null , false);
                if(txType_tiwen.isSelected()){
                    if(!imgluru.isSelected()) { //????????????????????????????????????
                        View view = LayoutInflater.from(getActivity()).inflate(R.layout.single_question_analysis, null, false);
                        showPopupWindow(view, 2);
                    }else{  //??????????????????
                        View view = LayoutInflater.from(getActivity()).inflate(R.layout.luru_question_analysis, null, false);
                        showPopupWindow_luru(view , 2);
                    }
                }else{
                    //??????????????????????????????
                    if(!txModle_luru.isSelected() || !imgluru.isSelected()){
                        //??????????????????????????????????????????
                        if(stuAnswer_selected.length() > 0){
//                            Toast.makeText(getActivity(),"????????????",Toast.LENGTH_SHORT).show();
                            View view = LayoutInflater.from(getActivity()).inflate(R.layout.suiji_qiangda_setanswer, null, false);
                            showPopupWindow_suiji_qiangda_setAnswer(view);
                        }else{
                            Toast.makeText(getActivity(),"????????????????????????",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        //????????????/????????????  ????????????   ????????????
        btEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txType_tiwen.isSelected()){
                    if(btEnd.getText().equals("????????????")){
                        //????????????????????????????????????????????????
                        beginOrEndQueAction("stopAnswer");
                        btEnd.setText("????????????");
//                        linear2.setVisibility(View.INVISIBLE);
                        jishiqi.stop();
                        if(mTimer_stuNum != null){
                            mTimer_stuNum.cancel();
                            mTimer_stuNum = null;
                        }
                    }else{
                        //????????????????????????????????????????????????
                        beginOrEndQueAction("startAnswer");
                        btEnd.setText("????????????");
                        linear2.setVisibility(View.VISIBLE);
                        tx_answers_sum.setText("0/60??????????????????");
                        jishiqi.setBase(SystemClock.elapsedRealtime());  //?????????????????? ???????????????0??????
                        jishiqi.start();   //????????????
                        //???????????????????????????????????????????????????
                        getSubmitAnswerStuNum();
                    }
                }else if(txType_suiji.isSelected()){
                    //?????????????????????(????????????)
//                    if(btEnd.getBackground().equals(getResources().getDrawable(R.drawable.btn_begin_enable))){
                    if(stuName_selected != null && stuName_selected.length() > 0){
                        //???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                        initData();
                        beginOrEndQueAction("startAnswerSuiji");

                        btSingle.setTextColor(Color.parseColor("#80000000"));
                        btAnswers.setTextColor(Color.parseColor("#80000000"));
                        btEnd.setTextColor(Color.parseColor("#80000000"));
                        btSingle.setBackground(getResources().getDrawable((R.drawable.btn_begin_unenable)));
                        btAnswers.setBackground(getResources().getDrawable((R.drawable.btn_begin_unenable)));
                        btEnd.setBackground(getResources().getDrawable((R.drawable.btn_begin_unenable)));

                        jishiqi.stop();
                        if(mTimer != null){
                            suiji_qiangda_flag = 0;
                            mTimer.cancel();
                        }
                        if(pw_selectStu != null){
                            suiji_qiangda_flag = 0;
                            pw_selectStu.dismiss();
                        }

                        linear2.setVisibility(View.VISIBLE);
                        jishiqi.setVisibility(View.INVISIBLE);
                        tx_answers_sum.setVisibility(View.VISIBLE);
                        tx_answers_sum.setText("?????????????????????");

                        View view_selectStu = LayoutInflater.from(getActivity()).inflate(R.layout.suiji_qiangda_selectstu, null, false);
                        //?????????????????????????????????/??????????????????
                        getSuijiOrQiangdaStu(view_selectStu);
                    }else{
                        Toast.makeText(getActivity(),"?????????????????????" + answer_sq,Toast.LENGTH_SHORT).show();
                    }
                }else if(txType_qiangda.isSelected()){
                    //?????????????????????
//                    if(btEnd.getBackground().equals(getResources().getDrawable(R.drawable.btn_begin_enable))){
                    if(stuName_selected != null && stuName_selected.length() > 0){
                        //???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                        initData();
                        beginOrEndQueAction("startQiangDa");

                        btSingle.setTextColor(Color.parseColor("#80000000"));
                        btAnswers.setTextColor(Color.parseColor("#80000000"));
                        btEnd.setTextColor(Color.parseColor("#80000000"));
                        btSingle.setBackground(getResources().getDrawable((R.drawable.btn_begin_unenable)));
                        btAnswers.setBackground(getResources().getDrawable((R.drawable.btn_begin_unenable)));
                        btEnd.setBackground(getResources().getDrawable((R.drawable.btn_begin_unenable)));

                        jishiqi.stop();
                        if(mTimer != null){
                            suiji_qiangda_flag = 0;
                            mTimer.cancel();
                        }
                        if(pw_selectStu != null){
                            suiji_qiangda_flag = 0;
                            pw_selectStu.dismiss();
                        }

                        linear2.setVisibility(View.VISIBLE);
                        jishiqi.setVisibility(View.INVISIBLE);
                        tx_answers_sum.setVisibility(View.VISIBLE);
                        tx_answers_sum.setText("?????????????????????");

                        View view_selectStu = LayoutInflater.from(getActivity()).inflate(R.layout.suiji_qiangda_selectstu, null, false);
                        //?????????????????????????????????/??????????????????
                        getSuijiOrQiangdaStu(view_selectStu);
                    }else{
                        Toast.makeText(getActivity(),"?????????????????????" + answer_sq,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //??????????????????????????????
        btClosed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txType_suiji.isSelected() || txType_qiangda.isSelected()){
//                    isClick_btClose = true;
                    if(mTimer != null){
                        suiji_qiangda_flag = 0;
                        mTimer.cancel();
                    }
                    if(pw_selectStu != null){
                        suiji_qiangda_flag = 0;
                        pw_selectStu.dismiss();
                    }

                    if(txType_suiji.isSelected()){
                        //?????????????????????????????????
                        beginOrEndQueAction("stopAnswerSuiji");
                    }else{
                        if(stuName_selected != null && stuAnswer_selected.length() > 0){
                            //??????????????????????????????
                            //???????????????????????????
                            beginOrEndQueAction("stopQiangDa");
                        }else{
                            //?????????????????????????????????
                            beginOrEndQueAction("stopAnswerQiangDa");
                        }
                    }
                }else{
                    //???????????????????????????????????????????????????????????????
                    if(btEnd.getText().equals("????????????")){
                        beginOrEndQueAction("stopAnswer");
                    }
                    if(mTimer_stuNum != null){
                        mTimer_stuNum.cancel();
                        mTimer_stuNum = null;
                    }
                }
                //????????????ui????????????????????????
                initData();
                setSelected1();
                setSelected2();
                txType_tiwen.setSelected(true);
                img_tiwen.setSelected(true);
                txChoose.setVisibility(View.INVISIBLE);
                linear_choose.setVisibility(View.INVISIBLE);
                linear1.setVisibility(View.GONE);
                jishiqi.stop();
                linear2.setVisibility(View.INVISIBLE);
            }
        });

        bindViews(); //UI??????????????????
        txType_tiwen.performClick();   //???????????????????????????????????????????????????????????????????????????????????????
        img_tiwen.performClick();

//        //????????????????????????????????????????????????????????????
//        Drawable[] drawable1 = txType_tiwen.getCompoundDrawables();
//        // ????????????0~3,?????????:????????????
//        drawable1[1].setBounds(0, 0, 45, 45);
//        txType_tiwen.setCompoundDrawables(null, drawable1[1], null, null);
//
//        Drawable[] drawable2 = txType_suiji.getCompoundDrawables();
//        drawable2[1].setBounds(0, 0, 45, 45);
//        txType_suiji.setCompoundDrawables(null, drawable2[1], null, null);
//
//        Drawable[] drawable3 = txType_qiangda.getCompoundDrawables();
//        drawable3[1].setBounds(0, 0, 45, 45);
//        txType_qiangda.setCompoundDrawables(null, drawable3[1], null, null);

        // Inflate the layout for this fragment
        return contentView;
    }

    @Override
    public void onClick(View v) {
//        jishiqi.stop();
        initData();
        chooseNum = 4;
        tx_choosenum.setText(chooseNum + "");
        if(mTimer != null){
            suiji_qiangda_flag = 0;
            mTimer.cancel();
        }
        if(pw_selectStu != null){
            suiji_qiangda_flag = 0;
            pw_selectStu.dismiss();
        }
        if(mTimer_stuNum != null){
            mTimer_stuNum.cancel();
            mTimer_stuNum = null;
        }
        if(v.getId() == txType_tiwen.getId() || v.getId() == img_tiwen.getId()) {
            setSelected1();
            txType_tiwen.setSelected(true);
            img_tiwen.setSelected(true);
        }else if(v.getId() == txType_suiji.getId() || v.getId() == img_suiji.getId()){
            setSelected1();
            txType_suiji.setSelected(true);
            img_suiji.setSelected(true);
        }else if(v.getId() == txType_qiangda.getId() || v.getId() == img_qiangda.getId()){
            setSelected1();
            txType_qiangda.setSelected(true);
            img_qiangda.setSelected(true);
        }else if(v.getId() == txModle_danxuan.getId() || v.getId() == imgdanxuan.getId()){
            txChoose.setVisibility(View.VISIBLE);
            linear_choose.setVisibility(View.VISIBLE);
            setSelected2();
            txModle_danxuan.setSelected(true);
            imgdanxuan.setSelected(true);
            btSingle.setText("????????????");
        }else if(v.getId() == txModle_duoxuan.getId() || v.getId() == imgduoxuan.getId()){
            txChoose.setVisibility(View.VISIBLE);
            linear_choose.setVisibility(View.VISIBLE);
            setSelected2();
            txModle_duoxuan.setSelected(true);
            imgduoxuan.setSelected(true);
            btSingle.setText("????????????");
        }else if(v.getId() == txModle_panduan.getId() || v.getId() == imgpanduan.getId()){
            txChoose.setVisibility(View.GONE);
            linear_choose.setVisibility(View.GONE);
            setSelected2();
            txModle_panduan.setSelected(true);
            imgpanduan.setSelected(true);
            btSingle.setText("????????????");
        }else if(v.getId() == txModle_luru.getId() || v.getId() == imgluru.getId()){
            txChoose.setVisibility(View.GONE);
            linear_choose.setVisibility(View.GONE);
            setSelected2();
            txModle_luru.setSelected(true);
            imgluru.setSelected(true);
            btSingle.setText("????????????");
        }

        //????????????????????????????????????
        if(isSelected()){
            btBegin1.setVisibility(View.GONE);
            btBegin2.setVisibility(View.VISIBLE);
            linear1.setVisibility(View.GONE);
            linear2.setVisibility(View.INVISIBLE);
        }else{
            txChoose.setVisibility(View.GONE);
            linear_choose.setVisibility(View.GONE);
            btBegin1.setVisibility(View.VISIBLE);
            btBegin2.setVisibility(View.GONE);
            linear1.setVisibility(View.GONE);
            linear2.setVisibility(View.INVISIBLE);
        }
    }
}