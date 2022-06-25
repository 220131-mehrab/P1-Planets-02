var welcomeMsg = 'Exploring Planets';
document.querySelector('h1').innerText = welcomeMsg;


fetch('/allplanets').then(resp => resp.json()).then(planets => {
    document.querySelector('#planet').innerHTML = listPlanets(planets);
});

let listPlanet = function(planet){
    return '<p>' + planet.planetID + ": " + planet.name + ": " + planet.mass + ": " + planet.moon + ": " + planet.distance + '</p>';
};

function listPlanets(json){
    //return `${json.map(listPlanet).join('\n')}`
    return `${json.map(listPlanet).join('\n')}`
};

function postPlanets(){
    let planet = {
        "planetID": document.getElementById("planetid").value,
        "name": document.getElementById("nameofplanet").value,
        "mass": document.getElementById("mass").value,
        "moon": document.getElementById("moons").value,
        "distance": document.getElementById("distance").value
    }
    //console.log(planet);
    fetch("/allplanets",{
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(planet)
    }).then((result) => {
        if(result.status != 200){
            throw new Error("Bad Server Response")
        }
        console.log(result.text());
    }).catch((error) => {console.log(error);})
    fetch('/allplanets').then(resp => resp.json()).then(planets => {
        document.querySelector('#planet').innerHTML = listPlanets(planets);
    });
    window.document.location.reload();
};