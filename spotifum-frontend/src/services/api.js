const BASE_URL = 'http://localhost:8080'

const getHeaders = () => ({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
})

export const auth = {
    register: (data) => fetch(`${BASE_URL}/auth/register`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    }).then(r => r.json()),

    login: (data) => fetch(`${BASE_URL}/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    }).then(r => r.json())
}

export const songs = {
    getAll: () => fetch(`${BASE_URL}/songs`, { headers: getHeaders() }).then(r => r.json()),
    play: (id) => fetch(`${BASE_URL}/songs/${id}/play`, { method: 'POST', headers: getHeaders() }).then(r => r.json())
}

export const albums = {
    getAll: () => fetch(`${BASE_URL}/albums`, { headers: getHeaders() }).then(r => r.json())
}

export const playlists = {
    getAll: () => fetch(`${BASE_URL}/playlists`, { headers: getHeaders() }).then(r => r.json()),
    create: (data) => fetch(`${BASE_URL}/playlists`, {
        method: 'POST',
        headers: getHeaders(),
        body: JSON.stringify(data)
    }).then(r => r.json()),
    addSong: (playlistId, songId) => fetch(`${BASE_URL}/playlists/${playlistId}/songs/${songId}`, {
        method: 'POST',
        headers: getHeaders()
    }).then(r => r.json()),
    delete: (id) => fetch(`${BASE_URL}/playlists/${id}`, {
        method: 'DELETE',
        headers: getHeaders()
    })
}

export const leaderboard = {
    get: () => fetch(`${BASE_URL}/leaderboard`, { headers: getHeaders() }).then(r => r.json())
}

export const user = {
    getMe: () => fetch(`${BASE_URL}/users/me`, { headers: getHeaders() }).then(r => r.json())
}