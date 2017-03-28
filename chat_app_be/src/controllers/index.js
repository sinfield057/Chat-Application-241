import express from 'express'
const router = express.Router();

router.use( '/user', require( './userController' ) );

module.exports = router;