package com.cube.autlab.UI;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.graphics.Typeface.*;



public class SelectionTextView extends AppCompatTextView {

    public JSONObject setObj = new JSONObject();
    private int startIndex = 0;
    private int line = 0;
    private int endIndex = 0;
    private int selectionColor = 0xFF33B5E5;
    private boolean isAllowSelectText = false;
    private boolean isNationCity = false;
    private boolean isPeNa = false;
    private boolean isFirst = false;
    private boolean isBefore = false;
    private boolean isOnScroll = false;
    private int scrollSpeed = 20;
    private SpannableString spannableString;
    private BackgroundColorSpan backgroundColorSpan;
    private UnderlineSpan under;
    private StyleSpan style1, style2, style3;  private Layout layout;
    private ScrollView scrollView;
    private JsonFormatTool formatTool = new JsonFormatTool();
    private boolean markFlag = false;

///////////////////////////////////////////////////////////////////////////////

    //TODO 保存json
    private static class outEntity{
        String name;
        int cnt, para;
    }

    private static class outRelation{
        String s1, s2;
        int cnt, para = 0;
        boolean isPut = true;
    }


    private outRelation nct;
    private outRelation pnt;

    private ArrayList<outEntity> nation = new ArrayList<outEntity>();
    private ArrayList<outEntity> city = new ArrayList<outEntity>();
    private ArrayList<outEntity> person = new ArrayList<outEntity>();
    private ArrayList<outEntity> time = new ArrayList<outEntity>();
    private ArrayList<outEntity> auth = new ArrayList<outEntity>();
    private ArrayList<outRelation> pena = new ArrayList<outRelation>();
    private ArrayList<outRelation> naci = new ArrayList<outRelation>();

    private int N = 200;
    private Integer[] paraStart= new Integer[200];
    private static int nationcnt = 0;
    private static int citycnt = 0;
    private static int nacicnt = 0;
    private static int personcnt = 0;
    private static int timecnt = 0;
    private static int authcnt = 0;
    private static int penacnt = 0;
    private String text = "";
    public void setPara(Integer[] arr){
        N = arr.length;
        paraStart = new Integer[N];
        paraStart = arr;
        for(int i = 0; i < paraStart.length; i++)
            System.out.println("length>>>>>"+paraStart[i]);
    }
    public void getText(String string){
        text = string;
    }
    public void setClear() throws JSONException{
        startIndex = 0;
        line = 0;
        endIndex = 0;
        nationcnt = 0;
        citycnt = 0;
        nacicnt = 0;
        personcnt = 0;
        timecnt = 0;
        authcnt = 0;
        penacnt = 0;
        setObj = new JSONObject();
        nation.clear();
        city.clear();
        person.clear();
        time.clear();
        auth.clear();
        pena.clear();
        naci.clear();
    }
    public void setJson() throws JSONException {
        boolean sth = false;
        markFlag = false;
        for (int i=1; i<=N&&paraStart[i-1]!=null; i++) {
            sth = false;
            System.out.println("para"+i+"     "+paraStart[i]);
            JSONObject pj = new JSONObject();
            JSONObject temp = new JSONObject();
            for (outEntity t : nation) {
                if (t.para == i) {
                    sth = true;
                    markFlag = true;
                    temp.put("item" + t.cnt, t.name);
                    //  System.out.println(">>nation>>"+t.cnt+"name : "+t.name);
                }
            }
            pj.put("国家", temp);
            temp = new JSONObject();
            for (outEntity t : city) {
                if (t.para == i) {
                    sth = true;
                    markFlag = true;
                    temp.put("item" + t.cnt, t.name);
                    //   System.out.println(">>城市>>" + t.cnt + "人名 : " + t.name);
                }
            }
            //  if(sth==true)
            pj.put("城市",temp);
            sth = false;
            temp = new JSONObject();
            for (outEntity t : person) {
                if (t.para == i) {
                    sth = true;
                    markFlag = true;
                    temp.put("item" + t.cnt, t.name);
                    //   System.out.println(">>city>>" + t.cnt + "name : " + t.name);
                }
            }
            pj.put("人名",temp);
            temp = new JSONObject();
            for (outEntity t : time) {
                if (t.para == i) {
                    sth = true;
                    markFlag = true;
                    temp.put("item" + t.cnt, t.name);
                    //     System.out.println(">>person>>" + t.cnt + "name : " + t.name);
                }
            }
            //     if(sth==true)
            pj.put("时间",temp);
            sth = false;
            temp = new JSONObject();
            for (outEntity t : auth) {
                if (t.para == i) {
                    sth = true;
                    markFlag = true;
                    temp.put("item" + t.cnt, t.name);
                    //   System.out.println(">>time>>" + t.cnt + "name : " + t.name);
                }
            }
            //     if(sth==true)
            pj.put("机构",temp);
            sth = false;

            JSONArray temp1 = new JSONArray();
            int num = 0;
            for (outRelation t : naci) {
                if (t.para == i) {
                    sth = true;
                    markFlag = true;
                    JSONObject ncObj = new JSONObject();
                    ncObj.put("城市" + t.cnt, t.s1);
                    //temp1.put(ncObj);
                    ncObj.put("国家" + t.cnt, t.s2);
                    temp1.put(num++, ncObj);
                    //  System.out.println(">>city/nation array>>" + temp1.toString());
                }
                //System.out.println("111>>city/nation array>>" + temp1.toString());
            }
            //   if(sth)
            pj.put("城市/国家",temp1);
            //   System.out.println(">>city/nation array**>>" + temp1.toJSONObject(temp1).toString());
            //System.out.println(">>city/nation array>>" + temp1.toString());

            temp1 = new JSONArray();
            num = 0;
            for (outRelation t : pena) {
                if (t.para == i){
                    sth = true;
                    markFlag = true;
                    JSONObject ncObj = new JSONObject();
                    ncObj.put("人名"+t.cnt,t.s1);
                    // temp1.put(ncObj);
                    ncObj.put("国籍"+t.cnt,t.s2);
                    temp1.put(num++,ncObj);
                }
            }
            //   if(sth)
            pj.put("人名/国籍",temp1);
            sth = false;

            if(markFlag) {
                if(i > 1)
                    setObj.put("语句" + i + ":" + text.substring(paraStart[i - 2], paraStart[i-1]), pj);
                else
                    setObj.put("语句" + i + ":" + text.substring(0, paraStart[i-1]), pj);
            }
            markFlag = false;
            System.out.println(">>人名/国籍 array>>" + pj.getJSONArray("人名/国籍").toString());
        }
    }

    public SelectionTextView(Context context) {
        this(context, null);
    }

    public SelectionTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectionTextView(Context context, AttributeSet attrs,
                             int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        backgroundColorSpan = new BackgroundColorSpan(selectionColor);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start,
                                 int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        spannableString = new SpannableString(getText());
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        scrollView = (ScrollView) getParent();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isAllowSelectText() == false) return super.onTouchEvent(event);

        layout = getLayout();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                removeSelection();
                try {
                    // 0 = getScrollY()
                    line = layout.getLineForVertical(0 + (int) event.getY());
                    startIndex = layout.getOffsetForHorizontal(line, (int) event.getX());
                } catch (Exception e) {

                }
                isOnScroll = true;
                break;
            case MotionEvent.ACTION_MOVE:
                spannableString.removeSpan(backgroundColorSpan);
                if (isOnScroll) {
                    if (event.getY() > scrollView.getScrollY()
                            + scrollView.getHeight()) {
                        scrollView.smoothScrollTo(0, scrollView.getScrollY()
                                + scrollSpeed); //  0 =scrollView.getScrollX()
                    } else if (event.getY() - scrollView.getScrollY() < 0) {
                        scrollView.smoothScrollTo(0, scrollView.getScrollY()
                                - scrollSpeed); //  0 =scrollView.getScrollX()
                    } else {
                    }
                }
                try {
                    // 0 = getScrollY()
                    line = layout.getLineForVertical(0 + (int) event.getY());
                    endIndex = layout.getOffsetForHorizontal(line, (int) event.getX());
                } catch (Exception e) {

                }

                if (startIndex < endIndex) {
                    spannableString.setSpan(backgroundColorSpan, startIndex,
                            endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    spannableString.setSpan(backgroundColorSpan, endIndex,
                            startIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }

                //TODO 即时标注
                if (isNationCity) {
                    if (isFirst){
                        if (!isBefore){
                            spannableString.removeSpan(style1);
                            if (startIndex < endIndex) {
                                spannableString.setSpan(style1,  startIndex,
                                        endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                nct.s1=spannableString.subSequence(startIndex, endIndex).toString();
                            } else {
                                spannableString.setSpan(style1,  endIndex,
                                        startIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                nct.s1=spannableString.subSequence(endIndex, startIndex).toString();
                            }
                        }
                    } else {
                        spannableString.removeSpan(under);
                        if (startIndex < endIndex) {
                            spannableString.setSpan(under,  startIndex,
                                    endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            nct.s2=spannableString.subSequence(startIndex, endIndex).toString();
                        } else {
                            spannableString.setSpan(under, endIndex,
                                    startIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            nct.s2=spannableString.subSequence(endIndex, startIndex).toString();
                        }
                    }
                }

                if (isPeNa) {
                    if (isFirst){
                        if (!isBefore){
                            spannableString.removeSpan(style2);
                            if (startIndex < endIndex) {
                                spannableString.setSpan(style2,  startIndex,
                                        endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                pnt.s1=spannableString.subSequence(startIndex, endIndex).toString();
                            } else {
                                spannableString.setSpan(style2,  endIndex,
                                        startIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                pnt.s1=spannableString.subSequence(endIndex, startIndex).toString();
                            }
                        }
                    } else {
                        spannableString.removeSpan(style3);
                        if (startIndex < endIndex) {
                            spannableString.setSpan(style3,  startIndex,
                                    endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            pnt.s2=spannableString.subSequence(startIndex, endIndex).toString();
                        } else {
                            spannableString.setSpan(style3, endIndex,
                                    startIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            pnt.s2=spannableString.subSequence(endIndex, startIndex).toString();
                        }
                    }
                }

                setText(spannableString);
                break;
            case MotionEvent.ACTION_UP:
                if (getSelectedText() != null) {
                    onActionUp();
                }
                isOnScroll = false;
                break;
            case MotionEvent.ACTION_CANCEL:
                removeSelection();
                break;
            default:
                break;
        }
        return true;
    }

    //todo changed
    public void removeSelection() {
        if (isNationCity) {
            if (isFirst){
                if (isBefore){
                    isBefore = false;
                } else {
                    isFirst = false;
                    if (nct.para == 0 ) {
                        for (int i = 0; i <= N; i++) {
                            if (paraStart[i] != null && startIndex < paraStart[i]) {
                                nct.para = i + 1;
                                break;
                            }else if(paraStart[i]==null&&paraStart[i-1]!=null){
                                nct.para = i + 1;
                                break;
                            }
                        }
                    }
                }
            } else {
                naci.add(nct);
                nct.isPut = false;
                isNationCity = false;
            }
        }

        if (isPeNa) {
            if (isFirst){
                if (isBefore){
                    isBefore = false;
                } else {
                    isFirst = false;
                    if (pnt.para == 0) {
                        for (int i = 0; i <= N; i++) {
                            if (paraStart[i] != null && startIndex < paraStart[i]) {
                                pnt.para = i + 1;
                                break;
                            }else if(paraStart[i]==null&&paraStart[i-1]!=null){
                                nct.para = i + 1;
                                break;
                            }
                        }
                    }
                }
            } else {
                pena.add(pnt);
                pnt.isPut = false;
                isPeNa = false;
            }
        }

        startIndex = 0;
        endIndex = 0;

        if (spannableString != null && backgroundColorSpan != null) {
            spannableString.removeSpan(backgroundColorSpan);
        }
    }

    public CharSequence getSelectedText() {
        if (startIndex == endIndex)
            return null;
        if (startIndex < endIndex)
            return getText().subSequence(startIndex, endIndex);
        return getText().subSequence(endIndex, startIndex);
    }

    public boolean isAllowSelectText() {
        return isAllowSelectText;
    }
    public void setAllowSelectText(boolean isSelectable) {
        this.isAllowSelectText = isSelectable;
    }

    //todo new sets
    public void setNation() {
        outEntity t = new outEntity();
        t.cnt = ++nationcnt;
        for (int i=0; i<=N; i++){
            if (paraStart[i]!=null&&startIndex<paraStart[i]){
                t.para = i+1;
                break;
            }else if(paraStart[i]==null&&paraStart[i-1]!=null){
                nct.para = i + 1;
                break;
            }
        }
        if (startIndex < endIndex) {
            spannableString.setSpan(new ForegroundColorSpan(0xFFFF6A6A), startIndex,
                    endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            t.name = spannableString.subSequence(startIndex, endIndex).toString();
        } else {
            spannableString.setSpan(new ForegroundColorSpan(0xFFFF6A6A), endIndex,
                    startIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            t.name = spannableString.subSequence(endIndex,startIndex).toString();
        }
        setText(spannableString);
        nation.add(t);
    }

    public void setCity() {
        outEntity t = new outEntity();
        t.cnt = ++citycnt;
        for (int i=0; i<=N; i++){
            if (paraStart[i]!=null&&startIndex<paraStart[i]){
                t.para = i+1;
                break;
            }else if(paraStart[i]==null&&paraStart[i-1]!=null){
                nct.para = i + 1;
                break;
            }
        }
        if (startIndex < endIndex) {
            spannableString.setSpan(new ForegroundColorSpan(0xFFFF8C69), startIndex,
                    endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            t.name = spannableString.subSequence(startIndex, endIndex).toString();
        } else {
            spannableString.setSpan(new ForegroundColorSpan(0xFFFF8C69), endIndex,
                    startIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            t.name = spannableString.subSequence(endIndex,startIndex).toString();
        }
        setText(spannableString);
        city.add(t);
    }

    public void setPerson() {
        outEntity t = new outEntity();
        t.cnt = ++personcnt;
        for (int i=0; i<=N; i++){
            if (paraStart[i]!=null&&startIndex<paraStart[i]){
                t.para = i+1;
                break;
            }else if(paraStart[i]==null&&paraStart[i-1]!=null){
                nct.para = i + 1;
                break;
            }
        }
        if (startIndex < endIndex) {
            spannableString.setSpan(new ForegroundColorSpan(0xFF8968CD), startIndex,
                    endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            t.name = spannableString.subSequence(startIndex, endIndex).toString();
        } else {
            spannableString.setSpan(new ForegroundColorSpan(0xFF8968CD), endIndex,
                    startIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            t.name = spannableString.subSequence(endIndex,startIndex).toString();
        }
        setText(spannableString);
        person.add(t);
    }
    public void setTime() {
        outEntity t = new outEntity();
        t.cnt = ++timecnt;
        for (int i=0; i<=N; i++){
            if (paraStart[i]!=null&&startIndex<paraStart[i]){
                t.para = i+1;
                break;
            }else if(paraStart[i]==null&&paraStart[i-1]!=null){
                nct.para = i + 1;
                break;
            }
        }
        if (startIndex < endIndex) {
            spannableString.setSpan(new ForegroundColorSpan(0xFF66CDAA), startIndex,
                    endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            t.name = spannableString.subSequence(startIndex, endIndex).toString();
        } else {
            spannableString.setSpan(new ForegroundColorSpan(0xFF66CDAA), endIndex,
                    startIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            t.name = spannableString.subSequence(endIndex,startIndex).toString();
        }
        setText(spannableString);
        time.add(t);
    }

    public void setAuthority() {
        outEntity t = new outEntity();
        t.cnt = ++authcnt;
        for (int i=0; i<=N; i++){
            if (paraStart[i]!=null&&startIndex<paraStart[i]){
                t.para = i+1;
                break;
            }else if(paraStart[i]==null&&paraStart[i-1]!=null){
                nct.para = i + 1;
                break;
            }
        }
        if (startIndex < endIndex) {
            spannableString.setSpan(new ForegroundColorSpan(0xFF698B69), startIndex,
                    endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            t.name = spannableString.subSequence(startIndex, endIndex).toString();
        } else {
            spannableString.setSpan(new ForegroundColorSpan(0xFF698B69), endIndex,
                    startIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            t.name = spannableString.subSequence(endIndex,startIndex).toString();
        }
        setText(spannableString);
        auth.add(t);
    }

    public void setNationCity() {
        if (isNationCity && nct.isPut)
            naci.add(nct);
        if (isPeNa && pnt.isPut)
            pena.add(pnt);
        isNationCity = true;
        isPeNa = false;
        isFirst = true;
        isBefore = true;
        nct = new outRelation();
        nct.cnt = ++nacicnt;
        style1 = new StyleSpan(BOLD);
        under = new UnderlineSpan();
    }

    public void setPeNa() {
        if (isNationCity && nct.isPut)
            naci.add(nct);
        if (isPeNa && pnt.isPut)
            pena.add(pnt);
        isNationCity = false;
        isPeNa = true;
        isFirst = true;
        isBefore = true;
        pnt = new outRelation();
        pnt.cnt = ++penacnt;
        style2 = new StyleSpan(BOLD_ITALIC);
        style3 = new StyleSpan(ITALIC);
    }

    public void setEnd() {
        if (isNationCity && nct.isPut)
            naci.add(nct);
        if (isPeNa && pnt.isPut)
            pena.add(pnt);
    }

    public void setScrollSpeed(int scrollSpeed) {
        this.scrollSpeed = scrollSpeed;
    }

    public void setSelectionColor(int selectionColor) {
        this.selectionColor = selectionColor;
    }

    public void onActionUp() {
    }
}
