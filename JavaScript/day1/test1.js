//truthy : true가 아닌 값도 true값을 가짐
if (true) {console.log('참')}
if ({}) {console.log('참')}
if ([]) {console.log('참')}
if (42) {console.log('참')}
if ("0") {console.log('참')}
if ("false") {console.log('참')}
if (new Date()) {console.log('참')}
if (-42) {console.log('참')}
if (12n) {console.log('참')}
if (3.14) {console.log('참')}
if (-3.14) {console.log('참')}
if (Infinity) {console.log('참')}
if (-Infinity) {console.log('참')}

//falsy
if (false) { console.log('거짓'); }
if (null) { console.log('거짓'); }
if (undefined) { console.log('거짓'); }
if (0) { console.log('거짓'); }
if (-0) { console.log('거짓'); }
if (0n) { console.log('거짓'); }
if (NaN) { console.log('거짓'); }
if ("") { console.log('거짓'); }
