const socket = new SockJS('/ws');
const stompClient = Stomp.over(socket);
// document.getElementById("request-location").addEventListener("click", requestLocationPermission);

stompClient.connect({}, () => {
    console.log("connected");
    stompClient.subscribe('/topic/get-location', (message) => {
        const data = JSON.parse(message.body);
        // if (data.latitude === null || data.longitude === null) {
        if (isNaN(data.latitude) || isNaN(data.longitude)) {
            removeMarker(data.id); // Remove marker if user disconnects
        } else {
            updateLocation(data.id, data.latitude, data.longitude);
        }
        // updateLocation(data.id, data.latitude, data.longitude);
    });
});

function requestLocationPermission() {
    console.log("permission button clicked");
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
            (position) => {
                console.log("Permission granted!");
                startTracking();
            },
            (error) => {
                console.error("Permission denied:", error);
            }
        );
    } else {
        console.error("Geolocation is not supported by this browser.");
    }
    // if (navigator.permissions) {
    //     navigator.permissions.query({ name: "geolocation" }).then((result) => {
    //         if (result.state === "granted") {
    //             console.log("Location access granted.");
    //             startTracking();
    //         } else if (result.state === "prompt") {
    //             console.log("Location permission needs to be granted.");
    //             navigator.geolocation.getCurrentPosition(
    //                 () => startTracking(),
    //                 (error) => console.error("Permission denied:", error)
    //             );
    //         } else {
    //             console.error("Location access denied.");
    //         }
    //     });
    // } else {
    //     startTracking(); // Fallback for browsers without permissions API
    // }
}

function startTracking() {
    if (navigator.geolocation) {
        navigator.geolocation.watchPosition(position => {
            sendLocation(position.coords.latitude, position.coords.longitude);
        }, error => {
            console.error("Error fetching location:", error);
        },{
            enableHighAccuracy:true,
            timeout:1000,
            maximumAge:0,
        });
    } else {
        console.error("Geolocation is not supported by this browser.");
    }
}

function sendLocation(latitude, longitude) {
    stompClient.send("/app/send-location", {}, JSON.stringify({ latitude, longitude }));
}

requestLocationPermission();

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
        // markers[id] = L.marker([latitude, longitude]).addTo(map);
    } else {
        markers[id] = L.marker([latitude, longitude]).addTo(map);
    }
    map.setView([latitude, longitude], 18); // Auto-center on new position
    // map.setView([latitude, longitude], 5); // Auto-center on new position
}

// Function to remove marker when a user disconnects
function removeMarker(id) {
    if (markers[id]) {
        map.removeLayer(markers[id]);
        delete markers[id];
        console.log(`Marker removed for disconnected user: ${id}`);
    }
}