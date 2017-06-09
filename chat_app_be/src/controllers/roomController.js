import express from 'express'
import session from 'express-session'
import User from '../models/user'
import Room from '../models/room'
import md5 from 'md5'
import mongoose from 'mongoose'
const router = express.Router();

router.post( '/createRoom' , ( req, res ) => {
	const username = req.session.username;
	const name = req.body.name;
	const description = req.body.description;
	const isPublic = req.body.isPublic;

	if (username != undefined) {

		Room.findOne( {
			name: name 
		}, 
		( err, foundRoom ) => {
			if ( err ) {
				res.send( {
					data: "Database Error: " + err,
					resolved: false
				} );
			} else if ( foundRoom ) {
				res.send( {
					data: 'Room name already in use!',
					resolved: false
				} );
			} else {
				const newRoom = new Room( {
					_id: mongoose.mongo.ObjectId(),
					name: name,
					description: description,
					admin: !isPublic ? username : null,
					users: [ username ],
					createdAt: Date.now()
				} );

				newRoom.save( ( err ) => {
					if ( err ) {
						res.send( {
							data: "Error creating room: " + err,
							resolved: false 
						} );
					} else {
						res.send( {
							data: "Room successfully created!",
							resolved: true
						} );
					}
				} );
			}
		} ); 
	} else {
		res.send( {
			data: "Invalid session",
			resolved: false
		} );
	}
} );

router.get( '/getRooms', ( req, res ) => {
	const username = req.session.username;
	
	if( typeof username !== 'undefined' ) {
		Room.find( {}, 
				   '_id name description users admin requests createdAt', 
		( err, rooms ) => {
			if( err ) {
				res.send( {
					data: "Error retrieving rooms: " + err,
					resolved: false
				} );
			} else {
				res.send( {
					data: rooms,
					resolved: true
				} );
			}
		} );
	} else {
		res.send( {
			data: "Invalid session",
			resolved: false
		} );
	}

} );

router.post('/getRoom', (req, res) => {
	const username = req.session.username;
	const name = req.body.name;

	if (username !== undefined) {
		Room.findOne({
			name: name
		}, '_id name description users admin requests createdAt',
		(err, room) => {
			if (err) {
				res.send({
					data: "Database error: " + err,
					resolved: false
				});
			} else if (room) {
				if (~room.users.indexOf(username)) {
					res.send({
						data: room,
						resolved: true
					});
				} else {
					res.send({
						data: "User " + username + " has not joined room " + name,
						resolved: false
					});
				}
			} else {
				res.send({
					data: "Room " + name + " not found",
					resolved: false
				});
			}
		});
	} else {
		res.send({
			data: "Invalid session",
			reoslved: false
		});
	}
});

router.post('/joinRoom', (req, res) => {
	const username = req.session.username;
	const name = req.body.name;

	if (username != undefined) {
		Room.findOne({
				name: name
			},
			(err, room) => {
				if (err) {
					res.send({
						data: "Database error: " + err,
						resolved: false
					})
				}
				else if (room) {
					let updatedUsers = room.users;
					updatedUsers.push(username);

					Room.update(
					{
						name: name
					},
					{
						users: updatedUsers
					},
					(err, rawResponse) => {
						if (err) {
							res.send({
								data: "Couldn't join the room: " + err,
								resolved: false
							});
						} else {
							res.send({
								data: "Joined room " + name,
								resolved: true
							});
						}
					});
				} else {
					res.send({
						data: "Room " + name + " not found",
						resolved: false
					});
				}
			}
		);
	} else {
		res.send({
			data: "Invalid session",
			resolved: false
		});
	}
});

router.post('/requestAccess', (req, res) => {
	const name = req.body.name;
	const requester = req.body.requester;

	Room.findOne(
		{
			name: name
		},
		(err, room) => {
			if (err) {
				res.send({
					data: "Database error: " + err,
					resolved: false
				})
			} else if (room) {
					if (~room.requests.indexOf(requester)) {
						res.send({
							data: "User " + requester + " already requested access to room " + name,
							resolved: false
						});
					} else {
						let updatedRequests = room.requests;
						updatedRequests.push(requester);

						Room.update(
							{
								name: name
							},
							{
								requests: updatedRequests
							},
							(err, rawResponse) => {
								if (err) {
									res.send({
										data: "Couldn't request access",
										resolved: false
									});
								} else {
									res.send({
										data: "Request sent",
										resolved: true
									});
								}
							}
						);
					}
			} else {
				res.send({
					data: "Room " + name + " not found",
					resolved: false
				});
			}
		}
	);
});

router.post('/acceptRequest', (req, res) => {
	const username = req.session.username;
	const name = req.body.name;
	const requester = req.body.requester;

	Room.findOne(
		{
			name: name
		},
		(err, room) => {
			if (err) {
				res.send({
					data: "Database error: " + err,
					resolved: false
				});
			} else if (room) {
				const index = room.requests.indexOf(requester);

				if (room.admin == username && ~index) {
					let updatedRequests = room.requests;
					let updatedUsers = room.users;

					updatedRequests.splice(index, 1);
					updatedUsers.push(requester);

					Room.update(
						{
							name: name
						},
						{
							requests: updatedRequests,
							users: updatedUsers
						},
						(err, rawResponse) => {
							if (err) {
								res.send({
									data: "Couldn't accept request",
									resolved: false
								});
							} else {
								res.send({
									data: "Request accepted",
									resolved: true
								});
							}
						}
					);
				} else {
					res.send({
						data: "Request not found",
						resolved: false
					});
				}
			} else {
				res.send({
					data: "Room " + name + " not found",
					resolved: false
				});
			}
		}
	);
});

router.post('/declineRequest', (req, res) => {
	const username = req.session.username;
	const name = req.body.name;
	const requester = req.body.requester;

	Room.findOne(
		{
			name: name
		},
		(err, room) => {
			if (err) {
				res.send({
					data: "Database error: " + err,
					resolved: false
				});
			} else if (room) {
				const index = room.requests.indexOf(requester);

				if (room.admin == username && ~index) {
					let updatedRequests = room.requests;
					updatedRequests.splice(index, 1);

					Room.update(
						{
							name: name
						},
						{
							requests: updatedRequests,
						},
						(err, rawResponse) => {
							if (err) {
								res.send({
									data: "Couldn't decline request",
									resolved: false
								});
							} else {
								res.send({
									data: "Request declined",
									resolved: true
								});
							}
						}
					);
				} else {
					res.send({
						data: "Request not found",
						resolved: false
					});
				}
			} else {
				res.send({
					data: "Room " + name + " not found",
					resolved: false
				});
			}
		}
	);
});


module.exports = router;