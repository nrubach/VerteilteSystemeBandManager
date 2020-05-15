const rootUrl = "http://localhost:8080/api"

document.addEventListener('DOMContentLoaded', () => {

	document.getElementById("rootBtn").addEventListener("click", function() {
		console.log("Root");
		getRoot()
			.then(data => {
				console.log(data);
				document.getElementById("resultbox").innerHTML = JSON.stringify(data, null, 2);
			});
	});

	document.getElementById("bandsBtn").addEventListener("click", () => {
		console.log("Bands");
		getBands()
			.then(data => {
				console.log(data);
				document.getElementById("resultbox").innerHTML = JSON.stringify(data, null, 2);
			});
	});

	document.getElementById("musiciansBtn").addEventListener("click", () => {
		console.log("Musicians");
		getMusicians()
			.then(data => {
				console.log(data);
				document.getElementById("resultbox").innerHTML = JSON.stringify(data, null, 2);
			});
	});

	document.getElementById("firstMusicianBtn").addEventListener("click", () => {
		console.log("First Musician");
		getFirstMusician()
			.then(data => {
				console.log(data);
				document.getElementById("resultbox").innerHTML = JSON.stringify(data, null, 2);
			});
	});

	document.getElementById("bandmusicians").addEventListener("click", () => {
		let id = document.getElementById("bandid").value;
		console.log("Musicians of Band " + id);
		getMusiciansOfBand(id)
			.then(data => {
				console.log(data);
				document.getElementById("resultbox").innerHTML = JSON.stringify(data, null, 2);
			});
	});

});

async function getRoot() {
	let response = await fetch(rootUrl);
	let data = await response.json();
	return data;
};

async function getBands() {
	let root = await getRoot();
	let bandsUrl = root["_links"]["bands"];
	let response = await fetch(bandsUrl.href);
	let data = await response.json();
	return data["_embedded"];
};

async function getMusicians() {
	let root = await getRoot();
	let musiciansUrl = root["_links"]["musicians"];
	let response = await fetch(musiciansUrl.href);
	let data = await response.json();
	return data["_embedded"];
}

async function getFirstMusician() {
	let musicians = await getMusicians();
	let musicianUrl = musicians["musicians"][0]["_links"]["self"];
	let response = await fetch(musicianUrl.href);
	let data = await response.json();
	return data;
}

async function getMusiciansOfBand(id) {
	let root = await getRoot();
	let bandsUrl = root["_links"]["bands"];
	let band = await fetch(bandsUrl.href + "/" + id);
	let bandData = await band.json();
	let musiciansOfBandUrl = bandData["_links"]["bandmusicians"];
	let response = await fetch(musiciansOfBandUrl.href);
	let data = await response.json();
	return data;
}
