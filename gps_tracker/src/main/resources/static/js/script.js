const socket = new SockJS('/ws');
const stompClient = Stomp.over(socket);

stompClient.connect({}, () => {
    console.log("connected");
    stompClient.subscribe('/topic/get-location', (message) => {
        const data = JSON.parse(message.body);
        updateLocation(data.id, data.latitude, data.longitude);
    });
});

function sendLocation(latitude, longitude) {
    stompClient.send("/app/send-location", {}, JSON.stringify({ latitude, longitude }));
}

if (navigator.geolocation) {
    navigator.geolocation.watchPosition(position => {
        sendLocation(position.coords.latitude, position.coords.longitude);
    });
}

const map = L.map("map").setView([0, 0], 5);
L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png").addTo(map);

const markers = {};
function updateLocation(id, latitude, longitude) {
    if (!latitude || !longitude) {
        console.error("Invalid location data received!");
        return;
    }
    if (markers[id]) {
        markers[id].setLatLng([latitude, longitude]);
    } else {
        markers[id] = L.marker([latitude, longitude]).addTo(map);
        map.setView([latitude, longitude], 5); // Auto-center on new position
    }
    // map.setView([latitude, longitude], 5); // Auto-center on new position
}
