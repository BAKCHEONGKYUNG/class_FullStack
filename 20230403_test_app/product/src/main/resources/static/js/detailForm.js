const $btnUpdate = document.getElementById('btnUpdate');
const $btnDelete = document.getElementById('btnDelete');
const $btnFindAll = document.getElementById('btnFindAll');


//상품 아이디
const $pid = document.getElementById('pid');


const btnUpdate_h = e => {
    globalThis.location.href = `/products/${$pid.value}/edit`;
}
const btnDelete_h = e => {
    if (confirm('삭제 하시겠습니까?')) {
        globalThis.location.href = `/products/${$pid.value}/del`;
    }
}
const btnFindAll_h = e => {
    globalThis.location.href = `/products`;
}

$btnUpdate.addEventListener('click', btnUpdate_h, false);
$btnDelete.addEventListener('click', btnDelete_h, false);
$btnFindAll.addEventListener('click', btnFindAll_h, false)