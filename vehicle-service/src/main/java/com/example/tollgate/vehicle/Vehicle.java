package com.example.tollgate.vehicle;

import com.example.tollgate.binding.Delegate;
import com.example.tollgate.model.Entity;
import com.example.tollgate.model.Message;
import org.apache.commons.scxml2.SCXMLListener;
import org.apache.commons.scxml2.env.AbstractStateMachine;
import org.apache.commons.scxml2.model.EnterableState;
import org.apache.commons.scxml2.model.ModelException;
import org.apache.commons.scxml2.model.Transition;
import org.apache.commons.scxml2.model.TransitionTarget;

import java.util.Random;

public class Vehicle extends AbstractStateMachine {

    public static String randomVehicleId() {
        char[] provinceAbbr = { // 省份简称 4+22+5+3
                '京', '津', '沪', '渝',
                '冀', '豫', '云', '辽', '黑', '湘', '皖', '鲁', '苏', '浙', '赣',
                '鄂', '甘', '晋', '陕', '吉', '闽', '贵', '粤', '青', '川', '琼',
                '宁', '新', '藏', '桂', '蒙',
                '港', '澳', '台'
        };
        String alphas = "QWERTYUIOPASDFGHJKLZXCVBNM1234567890"; // 26个字母 + 10个数字

        Random random = new Random(); // 随机数生成器

        String vid = "";

        // 省份+地区代码+·  如 湘A· 这个点其实是个传感器，不过加上美观一些
        vid += provinceAbbr[random.nextInt(34)]; // 注意：分开加，因为加的是2个char
        vid += alphas.charAt(random.nextInt(26)) + "·";

        // 5位数字/字母
        for (int i = 0; i < 5; i++) {
            vid += alphas.charAt(random.nextInt(36));
        }

        return vid;
    }


    public static final String SCXML_MODEL = "vehicle.xml";

    private String vid;


    public String getVehicleId() {
        return vid;
    }

    private Delegate delegate;

    public void setDelegate(Delegate delegate) {
        this.delegate = delegate;
        this.getEngine().addListener(this.getEngine().getStateMachine(), new SCXMLListener() {
            @Override
            public void onEntry(EnterableState enterableState) {
                Vehicle.this.delegate.send(new Message(
                        Entity.ANY.getId(),
                        Message.Header.STATUS_VEHICLE,
                        enterableState.getId())
                );
            }

            @Override
            public void onExit(EnterableState enterableState) {

            }

            @Override
            public void onTransition(TransitionTarget transitionTarget, TransitionTarget transitionTarget1, Transition transition, String s) {

            }
        });
    }

    public Vehicle(String vid) throws ModelException {
        super(Vehicle.class.getClassLoader().getResource(SCXML_MODEL));
        this.vid = vid;
    }

    @Override
    public boolean invoke(String methodName) {
        this.getLog().info(methodName);
        return true;
    }

    public Object[] getStates() {
        return this.getEngine().getStateMachine().getChildren().stream().map(s -> s.getId()).toArray();
    }
}
