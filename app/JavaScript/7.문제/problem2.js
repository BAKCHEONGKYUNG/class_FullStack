//문제 1)
//은행계좌 객체를 설계하고 입금, 출금, 조회 기능을 구현하시오(단, 생성자함수 사용할 것)
//속성: 잔액
//행위: 입금, 출금, 잔액조회

{
    //-설계서(정의)-
    function Acount(){
        Acount.count++; //정적 프로퍼티
        this.balance = 0;   //인스턴스 데이터 프로퍼티
    }
    //함수표현식(인스턴스 메소드 프로퍼티)
    Acount.prototype.deposit = function(money) {
        this.balance += money;
    }  
    Acount.prototype.withdraw = function(money) {
        this.balance -= money;
    }
    Acount.prototype.getBalance = function() {
        return this.balance;
    }

    //정적 데이터 프로퍼티(모든객체가 공유/ 생성X)
    Acount.count = 0;
    //정적 메소드 프로퍼티
    Acount.getCount = function(){
        return Acount.count;
    }
    //-------------------------

    //행위
    //계좌생성
    const account = new Acount();
    //잔액조회
    console.log(account.balance);
    //입금
    account.deposit(1000);
    account.deposit(2000);
    //잔액조회
    console.log(account.balance);
    //출금
    account.withdraw(1000);
    //잔액조회
    console.log(account.balance);


    //배열에 계좌 저장(어떤요소든지 가능)
    // const accounts = [new Acount(), new Acount(), new Acount()];
    const accounts = [];

    //계좌객체를 3개 생성 -> 배열에 저장
    for(let cnt = 1; cnt <= 3; cnt++){
        accounts.push(new Acount()); //push method
    }

    // 1번째 계좌에 1000원 입금
    accounts[0].deposit(1000);
    // 2번째 계좌에 2000원 입금
    accounts[1].deposit(2000);
    // 3번째 계좌에 3000원 입금
    accounts[2].deposit(3000);
    
    // 모든 계좌의 잔액 조회(배열을 순차적으로)
    for(let account of accounts){
        console.log(account.getBalance());
    }

    console.log(Acount.getCount());

}



//문제 2)
//은행계좌 객체를 설계하고 입금, 출금, 조회기능을 구현하시오.(단, class문법 사용할 것)
//속성: 잔액

{
    class Acount {
        //생성자
        constructor(){
            //인스턴스 데이터 프로퍼티
            this.balance = 0;
        }
        //인스턴스 메소드 프로퍼티
        deposit(money){
            this.balance += money; 
        }
        withdraw(money){
            this.balance -= money;
        }
        getBalance(){
            return this.balance;
        }

        //정적 데이터 프로퍼티
        static count = 0;
        //정적 메소드 프로퍼티
        static getCount(){
            return Acount.count;
        }
    }

    //행위
    //계좌생성
    const account = new Acount();
    //잔액조회
    console.log(account.balance);
    //입금
    account.deposit(1000);
    account.deposit(2000);
    //잔액조회
    console.log(account.balance);
    //출금
    account.withdraw(1000);
    //잔액조회
    console.log(account.balance);

    //배열에 계좌 저장(어떤요소든지 가능)
    // const accounts = [new Acount(), new Acount(), new Acount()];
    const accounts = [];

    //계좌객체를 3개 생성 -> 배열에 저장
    for(let cnt = 1; cnt <= 3; cnt++){
        accounts.push(new Acount()); //push method
    }

    // 1번째 계좌에 1000원 입금
    accounts[0].deposit(1000);
    // 2번째 계좌에 2000원 입금
    accounts[1].deposit(2000);
    // 3번째 계좌에 3000원 입금
    accounts[2].deposit(3000);
    
    // 모든 계좌의 잔액 조회(배열을 순차적으로)
    for(let account of accounts){
        console.log(account.getBalance());
    }

    console.log(Acount.getCount());

}
