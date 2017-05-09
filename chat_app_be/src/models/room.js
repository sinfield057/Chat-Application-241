import mongoose from 'mongoose'
const Schema = mongoose.Schema;

const roomSchema = new Schema( {
	_id: Schema.Types.ObjectId,
	name: String,
	description: String,
	users: [ { type: Schema.Types.ObjectId } ],
	admin: Schema.Types.ObjectId,
	createdAt: Date
} );

module.exports = mongoose.model( 'Room', roomSchema );