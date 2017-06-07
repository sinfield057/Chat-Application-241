import mongoose from 'mongoose'
const Schema = mongoose.Schema;

const roomSchema = new Schema( {
	_id: Schema.Types.ObjectId,
	name: String,
	description: String,
	users: [ { 
		type: Schema.Types.ObjectId 
	} ],
	messages: [ { 
		sender: String,
		message: String,
		dateSent: Date
	} ],
	admin: Schema.Types.ObjectId,
	requests: [ { type: Schema.Types.ObjectId } ],
	createdAt: Date
} );

module.exports = mongoose.model( 'Room', roomSchema );