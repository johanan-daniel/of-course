-- Status types
INSERT INTO Status VALUES (1, 'Applied')
ON CONFLICT (id)
DO NOTHING;
INSERT INTO Status VALUES (2, 'OA')
ON CONFLICT (id)
DO NOTHING;
INSERT INTO Status VALUES (3, 'Interviewing')
ON CONFLICT (id)
DO NOTHING;
INSERT INTO Status VALUES (4, 'Offered')
ON CONFLICT (id)
DO NOTHING;
INSERT INTO Status VALUES (5, 'Rejected')
ON CONFLICT (id)
DO NOTHING;
INSERT INTO Status VALUES (6, 'Abandoned')
ON CONFLICT (id)
DO NOTHING;

-- Interview stages
INSERT INTO Interview_Stage VALUES (1, 'Waiting')
ON CONFLICT (id)
DO NOTHING;
INSERT INTO Interview_Stage VALUES (2, 'Behavioral')
ON CONFLICT (id)
DO NOTHING;
INSERT INTO Interview_Stage VALUES (3, 'Auto - OA')
ON CONFLICT (id)
DO NOTHING;
INSERT INTO Interview_Stage VALUES (4, 'Technical - OA')
ON CONFLICT (id)
DO NOTHING;
INSERT INTO Interview_Stage VALUES (5, 'Technical - Live')
ON CONFLICT (id)
DO NOTHING;

-- Levels (for positions)
INSERT INTO Level VALUES (1, 'New grad')
ON CONFLICT (id)
DO NOTHING;
INSERT INTO Level VALUES (2, 'Entry level (0-3)')
ON CONFLICT (id)
DO NOTHING;
INSERT INTO Level VALUES (3, 'Maybe early career (<3)')
ON CONFLICT (id)
DO NOTHING;
INSERT INTO Level VALUES (4, 'Maybe not early career (not specified)')
ON CONFLICT (id)
DO NOTHING;
INSERT INTO Level VALUES (5, 'Not early career (2+)')
ON CONFLICT (id)
DO NOTHING;