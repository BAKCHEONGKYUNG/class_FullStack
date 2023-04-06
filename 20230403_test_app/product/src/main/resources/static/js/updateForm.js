const $btnSave = document.getElementById('btnSave');
const $btnCancel = document.getElementById('btnCancel');
const $btnFindAll = document.getElementById('btnFindAll');

const btnCancel_h = e => history.back()
const btnFindAll_h = e => location.href = '/products';

$btnCancel.addEventListener('click', btnCancel_h, false);
$btnFindAll.addEventListener('click', btnFindAll_h, false);


const add_h = e => {
    e.preventDefault();
    frm.submit();
}
$btnSave.addEventListener('click', add_h, false);