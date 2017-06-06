import express from 'express'
import session from 'express-session'
import User from '../models/user'
import Room from '../models/room'
import md5 from 'md5'
import mongoose from 'mongoose'
const router = express.Router();

router.post( '/createRoom' , ( req, res ) => {
	const userId 	  	= req.body.userId,
		  	name   	  	= req.body.name,
		  	description = req.body.description,
				isPublic 		= req.body.isPublic;

	if ( userId == req.session.userId ) {

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
					admin: !isPublic ? userId : null,
					users: [ userId ],
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
	const userId = req.session.userId;
	
	if( typeof userId !== 'undefined' ) {
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

router.post('/joinRoom', (req, res) => {
	const userId = req.session.userId;
	const name = req.body.name;

	if (userId !== undefined) {
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
					var updatedUsers = room.users;
					updatedUsers.push(userId);

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
	const requesterId = req.body.requesterId;
	console.log(name, requesterId);

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
					if (~room.requests.indexOf(requesterId)) {
						res.send({
							data: "User with id: " + requesterId + " already requested access to room " + name,
							resolved: false
						});
					} else {
						var updatedRequests = room.requests;
						updatedRequests.push(requesterId);

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


module.exports = router;