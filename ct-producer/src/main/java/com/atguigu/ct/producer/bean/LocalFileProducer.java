package com.atguigu.ct.producer.bean;

import com.atguigu.ct.common.bean.DataIn;
import com.atguigu.ct.common.bean.DataOut;
import com.atguigu.ct.common.bean.Producer;
import com.atguigu.ct.common.util.DateUtil;
import com.atguigu.ct.common.util.NumberUtil;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 本地数据文件生产者
 */
public class LocalFileProducer implements Producer {

    private DataIn in;
    private DataOut out;
    private volatile boolean flg = true;

    public void setIn(DataIn in) {
        this.in = in;
    }

    public void setOut(DataOut out) {
        this.out = out;
    }

    /**
     * 生产数据
     */
    public void produce() {

        try {
            // 读取通讯录数据
            List<Contact> contacts = in.read(Contact.class);// TODO: 2020/5/14 为啥要用class作为入参呢？  是为了通用性考虑
//            System.out.println(contacts);
            while ( flg ) {

                // 从通讯录中随机查找2个电话号码（主叫，被叫）
                // TODO: 2020/5/14 尽量使用 Math.random，new Random().nextInt是写在源码中的，可能被获取到的
                int call1Index = new Random().nextInt(contacts.size());
                int call2Index;
                while ( true ) {
                    call2Index = new Random().nextInt(contacts.size());
                    if ( call1Index != call2Index ) {
                        break;
                    }
                }

                Contact call1 = contacts.get(call1Index);
                Contact call2 = contacts.get(call2Index);

                // 生成随机的通话时间
                String startDate = "20180101000000";
                String endDate = "20190101000000";

                long startTime = DateUtil.parse(startDate, "yyyyMMddHHmmss").getTime();
                long endTime = DateUtil.parse(endDate, "yyyyMMddHHmmss").getTime();

                // 通话时间
                long calltime = startTime + (long)((endTime - startTime) * Math.random());
                // 通话时间字符串(通话建立的起始时间)
                String callTimeString = DateUtil.format(new Date(calltime), "yyyyMMddHHmmss");

                // 生成随机的通话时长
                // TODO: 2020/5/14 要求生成固定长度的时长字符串，使用工具类来做 decimalFormat.format(num)
                String duration = NumberUtil.format(new Random().nextInt(3000), 4);

                // 生成通话记录
                Calllog log = new Calllog(call1.getTel(), call2.getTel(), callTimeString, duration);

                System.out.println(log);
                // 将通话记录刷写到数据文件中
                out.write(log);

                Thread.sleep(500);
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭生产者
     * @throws IOException
     */
    public void close() throws IOException {
        if ( in != null ) {
            in.close();
        }

        if ( out != null ) {
            out.close();
        }
    }
}
