var welcomeMsg = 'Exploring Planets';
document.querySelector('h1').innerText = welcomeMsg;


fetch('/planets').then(resp => resp.json()).then(planets => {
    document.querySelector('body').innerHTML = listPlanet(planets);
});

let listPlanet = function(planet){
    return '<p>' + planets.planetId + ": " + planets.name + '</p>';
};

function listPlanets(json){
    return`
    ${json.map(listPlanet).join('\n')}
    `
};

function postPlanets(){
    let planet = {
        "planetId": document.getElementById("planetId").value,
        "name": document.getElementById("name").value
    }
    console.log(planet);
    fetch("/planets",{
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(planet)
    }).then((result) => {
        if(result.status != 200){
            throw new Error("Bad Serever Response")
        }
        console.log(result.text());
    }).catch((error) => {console.log(error);})
    fetch('/planets').then(resp => resp.json()).then(artist => {
        document.querySelector('body').innerHTML = listPlanet(planets);
    });
};