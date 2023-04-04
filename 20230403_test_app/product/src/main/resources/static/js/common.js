const ajax = {
    post(url, payload) {
      return fetch(url, {
        method: 'POST',
        headers: {
          Accept: 'application/json',
          'Content-type': 'application/json',
        },
        //자바스크립트 객체를 문자열로 반환
        body: JSON.stringify(payload),  //js객체 -> json포맷 문자열
      });
    },
    get(url) {
      return fetch(url, {
        method: 'GET',
        headers: {
          Accept: 'application/json',
        },
      });
    },
    patch(url, payload) {
      return fetch(url, {
        method: 'PATCH',
        headers: {
          Accept: 'application/json',
          'Content-type': 'application/json',
        },
        body: JSON.stringify(payload),
      });
    },
    delete(url) {
      return fetch(url, {
        method: 'DELETE',
        headers: {
          Accept: 'application/json',
        },
      });
    },
  };

  export{ ajax };