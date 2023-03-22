{
    // Date 1970.1.1 자칭(UTC)부터 초를 누적하여 관리 
    console.log(Date());                        //Wed Feb 08 2023 11:07:45 GMT+0900(대한민국 표준시()
    const today = new Date();
    console.log(today);                         //2023-02-08T02:15:04.555Z
    console.log(today.toString());              //2023-02-08T02:15:04.555Z
    console.log(today.getDate());               //8
    console.log(today.getFullYear());           //2023, 년도
    console.log(today.getMonth());              //1, 월( 0->1월, 1->2월, 2->3월,...11->12월)
    console.log(today.getDay());                //3, 요일( 0->일요일, 1->월요일, 2->화요일,...6->토요일)
    console.log(today.getHours());              //11, 시간
    console.log(today.getMinutes());            //13, 분
    console.log(today.getSeconds());            //4, 초
    console.log(today.getMilliseconds());       //65, 1/1000초
    console.log(today.toLocaleString());        //2023. 2. 8. 오전 11:13:04, 운영체제 시간
    console.log(today.toLocaleDateString());    //2023. 2. 8.
    console.log(today.toLocaleTimeString());    //오전 11:13:04
    console.log(Date.now());                    //1675822976687, 1970년 1월 1일 00:00:00 UTC
}
{
    const today = new Date();
    console.log(today);
    today.setFullYear(today.getFullYear()+1);
    today.setMonth(today.getMonth()+1);
    console.log(`다음달의 오늘날짜 : ${today}`);
    console.log(today.toLocaleDateString());
}
{
    //오늘날짜로 부터 35일 후의 요일은? (미래날짜)
    const today = new Date();
    console.log(today.toLocaleDateString());
    const future = new Date(today.getFullYear(), today.getMonth(), today.getDate() +35);
    console.log(future.toLocaleDateString());
    switch(future.getDay()){
        case 0:
            console.log(`일`);
            break;
        case 1:
            console.log(`월`);
            break;
        case 2:
            console.log(`화`);
            break;
        case 3:
            console.log(`수`);
            break;
        case 4:
            console.log(`목`);
            break;
        case 5:
            console.log(`금`);
            break;
        case 6:
            console.log(`토`);
            break;
    }
    
}
