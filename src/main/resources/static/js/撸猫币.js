


while(text("去浏览").exists()){ // 如果去浏览按钮存在则循环
    toast("现在就去浏览");
    text("去浏览").findOne().click();// 跳转
    sleep(3000);// 等待页面显示
    swipe(500, 1200, 500, 400, 1000);// 滑动
    sleep(4000);// 等待
    swipe(500, 1200, 500, 400, 1000);// 再次滑动
    sleep(12000);
    back();// 返回
    sleep(2000);
}

while(text("领取奖励").exists()){ // 如果领取按钮存在则循环
    toast("领取任务奖励");
    text("领取奖励").findOne().click();// 跳转
    sleep(1000);// 延迟1s
}

toast("任务完成，现在去撸猫");
text("关闭").findOne().click();// 关闭任务窗口
sleep(1000);
i = 0;
while(i < 100){
    //要支持的动作
    toast("开始撸猫");
    text("我的猫，点击撸猫").findOne().click();// 点猫
    sleep(300);
    i++;
}

toast("结束，请增删改查");


