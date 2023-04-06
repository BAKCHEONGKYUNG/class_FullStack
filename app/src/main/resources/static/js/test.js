import {ajax} from '/js/common.js';

        const $productFields = { pid, pname, quantity, price };

        const $allBtn = {
            toAdd: document.getElementById('toAddBtn'),     //신규
            list: document.getElementById('listBtn'),       //목록
            add: document.getElementById('addBtn'),         //등록
            find: document.getElementById('findBtn'),       //조회
            modify: document.getElementById('modifyBtn'),   //수정
            del: document.getElementById('delBtn')          //삭제
        };

        const $msg = {
            success: document.getElementById('globalSuccess'),
            err: document.getElementById('globalErr')
        };

        //글로벌 성공메세지
        const errMsg = msg => {
            $msg.success.textContent = '';
            $msg.err.textContent = msg;
        };

        //글로벌 에러메시지
        const successMsg = msg => {
            $msg.success.textContent = msg;
            $msg.err.textContent = '';
        };

        //상품등록양식 클리어
        const clearForm = () => {
            $productFields.pname.value = '';
            $productFields.quantity.value = '';
            $productFields.price.value = '';
        };

        //목록
        const makeRow = p =>
            `<div><input type='checkbox' name='chk' value='${p.pid}' /></div>` +
            `<div>${p.pid}</div>` +
            `<div>${p.pname}</div>` +
            `<div>${p.quantity}</div>` +
            `<div>${p.price}</div>`;

        const list = res => {
            if (res.header.rtcd == '00'){
                const html = res.data.map(p => makeRow(p)).join('');
                document.querySelector('.wrap .body').innerHTML = html;
            } else {
                throw new Error('${res.header.rtmsg}');
            }
        };

        const list_h = e => {
            const url = `http://localhost:9080/api/products`;
            ajax
                .get(url)
                .then(res => res.json())
                .then(list)
                .catch(err => errMsg(err.message));
        };

        $allBtn.list.addEventListener('click', list_h, false);

        //등록
        const add = res => {
            if(res.header.rtcd == '00'){
                successMsg(`상품번호: ${res.data.pid} 가 등록되었습니다.`);
                document.querySelector('form').reset();
                list_h();
            } else {
                throw new Error(`${res.header.rtmsg}`);
            }
        };

        const add_h = e => {
            const url = `http://localhost:9080/api/products`;
            const payload = {
                pname: $productFields.pname.value,
                quantity: $productFields.quantity.value,
                price: $productFields.price.value
            };

            //유효성체크
            ajax
                .post(url, payload)
                .then(res => res.json())
                .then(add)
                .catch(err => errMsg(err.message));
        };

        $allBtn.add.addEventListener('click', add_h, false);

        //조회
        const find = res => {
            if(res.header.rtcd == '00'){

                successMsg(`상품번호: ${res.date.pid} 가 조회되었습니다.`);

                $productFields.pid.value = res.data.pid;
                $productFields.pname.value = res.data.pname;
                $productFields.quantity.value = res.data.quantity;
                $productFields.price.value = res.data.price;

            } else if (res.header.rtcd == '99'){
                errMsg(`찾고자하는 상품이 없습니다.`);
                clearForm();
            } else {
                throw new Error(`${res.header.rtmsg}`);
            }
        };

        const find_h = (e, pid) => {
            const url = `http://localhost:9080/api/products/${pid}`;
            ajax
                .get(url)
                .then(res => res.json())
                .then(find)
                .catch(err => errMsg(err.message));
        };

        $allBtn.find.addEventListener(
            'click',
            e => {
                const pid = $productFields.pid.value.trim();
                if(!pid){
                    $msg.err = '상품아이디를 입력하세요.';
                    retuen;
                }

                //읽기모드 버튼 보이기
                const readMode = document.querySelector('.btngrp.read');
                readMode.classList.contains('hidden') &&
                    readMode.classList.remove('hidden');

                //등록버튼 숨기기
                const addMode = document.querySelector('.btngrp.add');
                addMode.classList.contains('hidden') ||
                    addMode.classList.add('hidden');

                    find_h(e, pid);
                },
                
            false
        );

        //수정
        const modify = res => {
            if(res.header.rtcd == '00'){
                successMsg(`상품번호: ${res.data.pid} 가 수정되었습니다.`);
                list_h();
            } else {
                throw new Error(`${res.header.rtmsg}`);
            }
        };

        const modify_h = (e, pid) => {
            const url = `http://localhost:9080/api/products/${pid}`;
            const payload = {
                pname: $productFields.pname.value,
                quantity: $productFields.quantity.value,
                price: $productFields.price.value
            };
            ajax
                .patch(url, payload)
                .then(res => res.json())
                .then(res => modify(res))
                .catch(err => errMsg(err.message));
        };

        $allBtn.modify.addEventListener(
            'click',
            e => {
                const pid = $productFields.pid.value.trim();
                modify_h(e, pid);
            },
            false,
        );

        //삭제
        const del = res => {
            if(res.header.rtcd == '00'){
                successMsg(`상품이 삭제되었습니다.`);
                document.querySelector('form').reset();
                list_h();
            } else {
                throw new Error(`${res.header.rtmsg}`);
            {
        };

        const del_h = (e, pid) => {
            const url = `http://localhost:9080/api/products/${pid}`;
            ajax
                .delete(url)
                .then(res => res.json())
                .then(res => del(res))
                .catch(err => errMsg(err.message));
        };

        $allBtn.del.addEventListener(
            'click',
            e => {
                const pid = $productFields.pid.value.trim();
                if(pid) {
                    //삭제팝업 띄우기
                    const $delPopup = document.getElementById('delPopup');
                    $delPopup.showModel();

                    //삭제팝업 닫칠 때 버튼 값 읽어와서 del이면 삭제 진행
                    $delPopup.addEventLister(
                        'close',
                         e => {
                            if($delPopup.returnValue != 'del') return;
                            del_h(e, pid);
                        },
                        false,
                    );
                    return;
            } else {
                errMsg('삭제할 상품번호를 입력하세요');
                $productFields.pid.focus();
            }
        },
        false,
    );

        //신규
        $allBtn.toAdd.addEventListener(
            'click',
            e => {
                //읽기모드 버튼 숨기기
                const readMode = document.querySelector('.btngrp.read');
                readMode.classList.contains('hidden')||
                    readMode.classList.add('hidden');

                //등록버튼 보이기
                const addMode = document.querySelector('.btngrp.add');
                addMode.classList.contains('hidden') &&
                    addMode.classList.remove('hidden');

                //상품 등록 양식 클리어
                document.querySelector('form').reset();
            },
            false
        );

        //선택삭제
        const $all = document.getElementById('all');
        const all_h = e => {
            const $chks = document.querySelectorAll('#productList input[name=chk]');

            //체크된 체크박스 갯수
            const chkCnt = Array.from($chks).filter(input => input.checked).length;

            if($chks.length == chkCnt){
                Array.from($chks).forEach(input => input.checked = false);
            } else {
                Array.from($chks).forEach(input => input.checked = true);
            }
        }

        $all.addEventListener('click', all_h, false);

        //선택된 삭제 처리
        const $delPartsBtn = document.getElementById('delPartsBtn');
        const delParts = res => {
            if(res.header.rtcd == 0){
                list_h();
            }
        }
        const delParts_h = e => {
            if(!confirm('삭제하시겠습니까?')){
                return;
            }
            const $chks = document.querySelectorAll('#productList input[name=chk]');
            const chks = Array.from($chks).filter(input => input.checked)
                                          .map(input => input.value);
            const url = '/api/products/items/del';
            ajax.post(url, chks)
                .then(res => res.json())
                .then(delParts)
                .catch(err => console.log(err.message));
        }
        $delPartsBtn.addEventListener('click', delParts_h, false);

        //목록 가져오기
        list_h();