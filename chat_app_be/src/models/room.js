import mongoose from 'mongoose'
const Schema = mongoose.Schema;

const roomSchema = new Schema( {
	_id: Schema.Types.ObjectId,
	name: String,
	description: String,
	users: [ String ],
	admin: String,
	requests: [ String ],
	createdAt: Date
} );

module.exports = mongoose.model( 'Room', roomSchema );