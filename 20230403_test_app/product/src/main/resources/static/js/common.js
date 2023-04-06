const ajax = {
  post(url, payload) {
    return fetch(url, {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-type': 'application/json',
      },
      body: JSON.stringify(payload),
    });
  },
  get(url) {
    return fetch(url,{
      method: 'GET',
      headers: {
        Accept: 'application/json',
      },
    })
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    })
    .catch(error => {
      console.error('Error while parsing JSON:', error);
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

export { ajax };