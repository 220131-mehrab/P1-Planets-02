var welcomeMsg = 'Exploring Dwarf Planets';
document.querySelector('h1').innerText = welcomeMsg;


fetch('/allplanets').then(resp => resp.json()).then(dwarfplanets => {
    document.querySelector('#dwarfplanets').innerHTML = listPlanets(dwarfplanets);
});

let listPlanet = function(planet){
    return '<p>' + planet.planetID + ": " + planet.name + ": " + planet.mass + ": " + planet.moon + ": " + planet.distance + '</p>';
};

function listPlanets(json){
    return `${json.map(listPlanet).join('\n')}`
};

function postDwarfPlanets(){
    let planet = {
        "planetID": document.getElementById("dwarfplanetid").value,
        "name": document.getElementById("nameofdwarfplanet") .value,
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
    fetch('/allplanets').then(resp => resp.json()).then(dwarfplanets => {
        document.querySelector('#dwarfplanets').innerHTML = listPlanets(dwarfplanets);
    });
     window.document.location.reload();
};