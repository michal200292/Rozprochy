# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# source: match.proto
# Protobuf Python Version: 5.26.1
"""Generated protocol buffer code."""
from google.protobuf import descriptor as _descriptor
from google.protobuf import descriptor_pool as _descriptor_pool
from google.protobuf import symbol_database as _symbol_database
from google.protobuf.internal import builder as _builder
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()




DESCRIPTOR = _descriptor_pool.Default().AddSerializedFile(b'\n\x0bmatch.proto\"\t\n\x07Nothing\"/\n\x0cSubscription\x12\x0c\n\x04team\x18\x01 \x01(\t\x12\x11\n\tsubscribe\x18\x02 \x01(\x08\" \n\x11SubscriptionReply\x12\x0b\n\x03msg\x18\x01 \x03(\t\"#\n\x04Team\x12\x0c\n\x04name\x18\x01 \x01(\t\x12\r\n\x05short\x18\x02 \x01(\t\"\xb0\x01\n\x05Stats\x12\x18\n\x04info\x18\x01 \x01(\x0e\x32\n.MatchInfo\x12\x15\n\x05names\x18\x02 \x01(\x0b\x32\x06.Teams\x12\x15\n\x05score\x18\x03 \x01(\x0b\x32\x06.Score\x12\x0e\n\x06minute\x18\x04 \x01(\x05\x12\x15\n\x05shots\x18\x05 \x01(\x0b\x32\x06.Shots\x12\x1f\n\npossession\x18\x06 \x01(\x0b\x32\x0b.Possession\x12\x17\n\x06passes\x18\x07 \x01(\x0b\x32\x07.Passes\"%\n\x05Score\x12\r\n\x05team1\x18\x01 \x01(\x05\x12\r\n\x05team2\x18\x02 \x01(\x05\"3\n\x05Teams\x12\x14\n\x05team1\x18\x01 \x01(\x0b\x32\x05.Team\x12\x14\n\x05team2\x18\x02 \x01(\x0b\x32\x05.Team\"%\n\x05Shots\x12\r\n\x05team1\x18\x01 \x01(\x05\x12\r\n\x05team2\x18\x02 \x01(\x05\"*\n\nPossession\x12\r\n\x05team1\x18\x01 \x01(\x02\x12\r\n\x05team2\x18\x02 \x01(\x02\"&\n\x06Passes\x12\r\n\x05team1\x18\x01 \x01(\x05\x12\r\n\x05team2\x18\x02 \x01(\x05*\x99\x01\n\tMatchInfo\x12\x14\n\x10MATCH_INFO_START\x10\x00\x12\x13\n\x0fMATCH_INFO_GOAL\x10\x01\x12\x1e\n\x1aMATCH_INFO_MID_MATCH_STATS\x10\x02\x12\x1f\n\x1bMATCH_INFO_FIRST_HALF_STATS\x10\x03\x12 \n\x1cMATCH_INFO_WHOLE_MATCH_STATS\x10\x04\x32\xa5\x01\n\tPublisher\x12&\n\x11GetAvailableTeams\x12\x08.Nothing\x1a\x05.Team0\x01\x12\"\n\x0cStreamEvents\x12\x08.Nothing\x1a\x06.Stats0\x01\x12\x30\n\tSubscribe\x12\r.Subscription\x1a\x12.SubscriptionReply(\x01\x12\x1a\n\x04Ping\x12\x08.Nothing\x1a\x08.NothingB\x1f\n\x0bsr.grpc.genB\x0eStreamingProtoP\x01\x62\x06proto3')

_globals = globals()
_builder.BuildMessageAndEnumDescriptors(DESCRIPTOR, _globals)
_builder.BuildTopDescriptorsAndMessages(DESCRIPTOR, 'match_pb2', _globals)
if not _descriptor._USE_C_DESCRIPTORS:
  _globals['DESCRIPTOR']._loaded_options = None
  _globals['DESCRIPTOR']._serialized_options = b'\n\013sr.grpc.genB\016StreamingProtoP\001'
  _globals['_MATCHINFO']._serialized_start=541
  _globals['_MATCHINFO']._serialized_end=694
  _globals['_NOTHING']._serialized_start=15
  _globals['_NOTHING']._serialized_end=24
  _globals['_SUBSCRIPTION']._serialized_start=26
  _globals['_SUBSCRIPTION']._serialized_end=73
  _globals['_SUBSCRIPTIONREPLY']._serialized_start=75
  _globals['_SUBSCRIPTIONREPLY']._serialized_end=107
  _globals['_TEAM']._serialized_start=109
  _globals['_TEAM']._serialized_end=144
  _globals['_STATS']._serialized_start=147
  _globals['_STATS']._serialized_end=323
  _globals['_SCORE']._serialized_start=325
  _globals['_SCORE']._serialized_end=362
  _globals['_TEAMS']._serialized_start=364
  _globals['_TEAMS']._serialized_end=415
  _globals['_SHOTS']._serialized_start=417
  _globals['_SHOTS']._serialized_end=454
  _globals['_POSSESSION']._serialized_start=456
  _globals['_POSSESSION']._serialized_end=498
  _globals['_PASSES']._serialized_start=500
  _globals['_PASSES']._serialized_end=538
  _globals['_PUBLISHER']._serialized_start=697
  _globals['_PUBLISHER']._serialized_end=862
# @@protoc_insertion_point(module_scope)
