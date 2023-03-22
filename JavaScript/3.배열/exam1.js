//1.배열 생성
{
    //빈 배열 생성
    const arr = [];

    //배열 요소 3개를 가질 수 있는 배열 생성
    const arr2 = new Array(3);  //[empty, empty, empty]

    //
    const arr3 = [1,2,3];
    const arr4 = new Array(1,2,3);
}

//2.요소 추가 push();
{
    {   //push & pop 배열 맨 뒤에서 추가, 제거
        const arr = [`😊`,`😂`,`🤣`];
        arr.push(`😒`);
        
        console.log(arr);   //[ '😊', '😂', '🤣', '😒' ]
        console.log(arr.pop());
        console.log(arr);   //[`😊`,`😂`,`🤣`]
    }
    {
        //unshift & shift 배열 맨 앞에서 추가, 제거 
        const arr = [`😊`,`😂`,`🤣`];
        arr.unshift(`👌`); 
        console.log(arr);    //[ '[ '😊', '😂', '🤣' ]', '😊', '😂', '🤣' ]
        arr.shift()
        console.log(arr); //[ '😊', '😂', '🤣' ]

    }
    {
        //splice 요소제거 대체
        const arr = [ '😊', '😂', '🤣' ];
        arr.splice(1,0,'😊', '😂');  //1 인덱스부터 끝까지 요소 제거
        console.log(arr); // [ '😊', '😊', '😂', '😂', '🤣' ]
    } 
}


//3.요소 추출(=제거) pop, shift, splice


//4.요소 삭제 delete
{
    const arr = ['😊', '😂', '🤣'];
    delete arr[1];
    console.log(arr); //[ '😊', <1 empty item>, '🤣' ] <= 희소배열(배열이 비어있는)
    console.log(arr.length); //3
}

//5.요소 대체 splice


//6.요소 탐색 
{
    const arr = ['😊', '😂', '🤣'];
    const firstEle = arr[0]; //첫번째 요소
    const lastEle = arr[arr.length-1]; //마지막 요소
    console.log(firstEle,lastEle); //😊 🤣
    console.log(arr); //[ '😊', '😂', '🤣' ]
}
{
    //at 원하는 인덱스의 요소 접근
    const arr = ['😊', '😂', '🤣'];
    const firstEle = arr.at(0); //첫번째 요소
    const lastEle = arr.at[-1]; //마지막 요소
    console.log(firstEle,lastEle); //😊 🤣
    console.log(arr); //[ '😊', '😂', '🤣' ]
}
{
    //indexOf 찾고자하는 첫번째 요소의 인덱스를 반환, 없으면 -1반
    const arr = ['😊', '😂', '🤣','😂'];
    const findedIndex = arr.indexOf('😂');
    console.log(findedIndex);   //1

    const findedIndex2 = arr.indexOf(`😍`);
    console.log(findedIndex2);  //-1
    
    const findedIndex3 = arr.indexOf('😂',2);   //검색 시작위치를 2번째 매개값으로 전달할 수 있다.
    console.log(findedIndex3);  //3
}
{
    //include 찾고자 하는 요소의 유무 판단
    const arr = ['😊', '😂', '🤣'];
    if(arr.includes(`🤣`)){ //있다
        console.log(`있다`);
    }else{
        console.log(`없다`);
    }

    if(arr.includes(`😍`)){ //없다
        console.log(`있다`);
    }else{
        console.log(`없다`);
    }
}

//7.요소 결합
{
    const arr = [`울산`, `kh`, '정보교육원'];
    const result1 = arr.join();
    console.log(result1);   //울산,kh,정보교육원

    const result2 = arr.join(``);
    console.log(result2);   //울산kh정보교육원

    const result3 = arr.join(`**`);
    console.log(result3);   //울산**kh**정보교육원
}
{
    //concat 배열을 합쳐서 새로운 배열로 반환
    const arr1 = [1,2,3];
    const arr2 = [5,6,7];
    const arr3 = [10,20,30];
    const result = arr1.concat(arr2,arr3);  
    console.log(result);    //[1,  2,  3,  5, 6, 7, 10, 20, 30]
    console.log(arr1, arr2, arr3);  //[ 1, 2, 3 ] [ 5, 6, 7 ] [ 10, 20, 30 ]
}


//10.배열 머지(합친다)
{
    //concat 배열을 합쳐서 새로운 배열로 반환
    const arr1 = [1,2,3];
    const arr2 = [5,6,7];
    const arr3 = [10,20,30];
    const result = arr1.concat(arr2,arr3);  
    console.log(result);    //[1,  2,  3,  5, 6, 7, 10, 20, 30]
    console.log(arr1, arr2, arr3);  //[ 1, 2, 3 ] [ 5, 6, 7 ] [ 10, 20, 30 ]
}


//11.중첩배열 평탄화
{
    //flat 중첩된 배열요소를 원하는 차원까지 평탄화
    const arr1 = [1, 2, [3, 4]];
    arr1.flat();    // [1, 2, 3, 4]

    const arr2 = [1, 2, [3, 4, [5, 6]]];
    arr2.flat();    // [1, 2, 3, 4, [5, 6]]

    const arr3 = [1, 2, [3, 4, [5, 6]]];
    arr3.flat(2);   // [1, 2, 3, 4, 5, 6]

    const arr4 = [1, 2, [3, 4, [5, 6, [7, 8, [9, 10]]]]];
    arr4.flat(Infinity);    // [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
}
{
    //배열 참조 arr, arr2가 같은 배열을 가리킴
    const arr = [1,2,3];
    const arr2 = arr;
    arr2[0] = 4;
    console.log(arr[0], arr2[0]);
}
{
    {   //배열 복사
        //case1) splice
        const arr = [1,2,3];
        const arr2 = arr.slice(0);
        console.log(arr,arr2);
        arr[0] = 4;
        console.log(arr, arr2);
    }
    {
        //배열 디스트리뷰션 (ES6)
        //case2)
        const arr = [1,2,3];
        const arr2 = [...arr]; //배열 복사
        console.log(arr,arr2);  //[ 1, 2, 3 ] [ 1, 2, 3 ]
        arr[0] = 4;
        console.log(arr,arr2);  //[ 4, 2, 3 ] [ 1, 2, 3 ]

    }
}
