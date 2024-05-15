import common.utility.JsonHandle;

public class test {
    /*public static void main(String[] args) {
        String json = "{\n" +
                "  \"act\": [\n" +
                "    {\n" +
                "      \"game_name\": \"BE.PV02AiSpeakingWords(Clone)\",\n" +
                "      \"file_zip\": \"App/zip/activity/1503140-43045-upt.zip\",\n" +
                "      \"download_error\": \"success\",\n" +
                "      \"turn\": [\n" +
                "        {\n" +
                "          \"right_answer\": \"\",\n" +
                "          \"word\": [\n" +
                "            {\n" +
                "              \"path\": \"6a0d238d-4836-487b-b86e-dca33826e5d3_265871.zip\",\n" +
                "              \"image\": [],\n" +
                "              \"word_id\": 40060094,\n" +
                "              \"text\": \"how\",\n" +
                "              \"audio\": [\n" +
                "                {\n" +
                "                  \"duration\": 0,\n" +
                "                  \"file_path\": \"yq48vop13R8T4uARYtr89UUST6qJeFxw.mp3\",\n" +
                "                  \"voices_type_id\": 52,\n" +
                "                  \"tag_title\": \"how\",\n" +
                "                  \"link\": \"audio/yq48vop13R8T4uARYtr89UUST6qJeFxw.mp3\",\n" +
                "                  \"name_original\": \"how - female\",\n" +
                "                  \"voices_id\": 52,\n" +
                "                  \"id\": 77959,\n" +
                "                  \"sync_data\": \"\"\n" +
                "                }\n" +
                "              ],\n" +
                "              \"type\": \"question\",\n" +
                "              \"order\": 0\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"right_answer\": \"\",\n" +
                "          \"word\": [\n" +
                "            {\n" +
                "              \"path\": \"0dc59fa0-6159-4543-b36b-4233242e24f8_265872.zip\",\n" +
                "              \"image\": [],\n" +
                "              \"word_id\": 40060095,\n" +
                "              \"text\": \"old\",\n" +
                "              \"audio\": [\n" +
                "                {\n" +
                "                  \"duration\": 1,\n" +
                "                  \"file_path\": \"r9HQLsmmG2AYGYZpqY2gfSjprZASh1Fa.mp3\",\n" +
                "                  \"voices_type_id\": 52,\n" +
                "                  \"tag_title\": \"old\",\n" +
                "                  \"link\": \"audio/r9HQLsmmG2AYGYZpqY2gfSjprZASh1Fa.mp3\",\n" +
                "                  \"name_original\": \"old - male\",\n" +
                "                  \"voices_id\": 52,\n" +
                "                  \"id\": 87189,\n" +
                "                  \"sync_data\": \"\"\n" +
                "                }\n" +
                "              ],\n" +
                "              \"type\": \"question\",\n" +
                "              \"order\": 0\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ],\n" +
                "      \"game_id\": 1000083\n" +
                "    },\n" +
                "    {\n" +
                "      \"game_name\": \"BE.PS04SpeakingCompetition(Clone)\",\n" +
                "      \"file_zip\": \"App/zip/activity/1503141-43045-qta.zip\",\n" +
                "      \"download_error\": \"success\",\n" +
                "      \"turn\": [\n" +
                "        {\n" +
                "          \"right_answer\": \"\",\n" +
                "          \"word\": [\n" +
                "            {\n" +
                "              \"path\": \"983fb275-6f55-434f-bffa-77fd7398f8ef_265873.zip\",\n" +
                "              \"image\": [\n" +
                "                {\n" +
                "                  \"file_path\": \"ca489cdd-addc-47ce-a0e1-a465b8457282.png\",\n" +
                "                  \"file_type\": \"\",\n" +
                "                  \"link\": \"images/ca489cdd-addc-47ce-a0e1-a465b8457282.png\",\n" +
                "                  \"images_categories_id\": 0,\n" +
                "                  \"id\": 310431\n" +
                "                }\n" +
                "              ],\n" +
                "              \"word_id\": 40060096,\n" +
                "              \"text\": \"name\",\n" +
                "              \"audio\": [\n" +
                "                {\n" +
                "                  \"duration\": 0,\n" +
                "                  \"file_path\": \"cfdfd510-e4a6-4ba2-b2f8-e8424682b66f.mp3\",\n" +
                "                  \"voices_type_id\": 65,\n" +
                "                  \"tag_title\": \"name\",\n" +
                "                  \"link\": \"audio/cfdfd510-e4a6-4ba2-b2f8-e8424682b66f.mp3\",\n" +
                "                  \"name_original\": \"\",\n" +
                "                  \"voices_id\": 65,\n" +
                "                  \"id\": 166050,\n" +
                "                  \"sync_data\": \"\"\n" +
                "                }\n" +
                "              ],\n" +
                "              \"type\": \"question\",\n" +
                "              \"order\": 0\n" +
                "            },\n" +
                "            {\n" +
                "              \"path\": \"fda2f0eb-91b0-4842-95db-e3a07cb31d99_265874.zip\",\n" +
                "              \"image\": [],\n" +
                "              \"word_id\": 40060097,\n" +
                "              \"text\": \"name\",\n" +
                "              \"audio\": [\n" +
                "                {\n" +
                "                  \"duration\": 0,\n" +
                "                  \"file_path\": \"88e1063a-51cd-4e19-be33-cb19c006021f.mp3\",\n" +
                "                  \"voices_type_id\": 68,\n" +
                "                  \"tag_title\": \"name\",\n" +
                "                  \"link\": \"audio/88e1063a-51cd-4e19-be33-cb19c006021f.mp3\",\n" +
                "                  \"name_original\": \"\",\n" +
                "                  \"voices_id\": 68,\n" +
                "                  \"id\": 166051,\n" +
                "                  \"sync_data\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                  \"duration\": 0,\n" +
                "                  \"file_path\": \"498f0429-092a-4c40-9454-370f3a7b56ce.mp3\",\n" +
                "                  \"voices_type_id\": 67,\n" +
                "                  \"tag_title\": \"name\",\n" +
                "                  \"link\": \"audio/498f0429-092a-4c40-9454-370f3a7b56ce.mp3\",\n" +
                "                  \"name_original\": \"\",\n" +
                "                  \"voices_id\": 67,\n" +
                "                  \"id\": 166052,\n" +
                "                  \"sync_data\": \"\"\n" +
                "                }\n" +
                "              ],\n" +
                "              \"type\": \"question_answer\",\n" +
                "              \"order\": 0\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"right_answer\": \"\",\n" +
                "          \"word\": [\n" +
                "            {\n" +
                "              \"path\": \"2f05777c-a3e5-4c4c-a14a-a3d251976342_265875.zip\",\n" +
                "              \"image\": [\n" +
                "                {\n" +
                "                  \"file_path\": \"Tp6W0v39j5ZtT0Rpw1S0hrtmZ6ALoZRX.png\",\n" +
                "                  \"file_type\": \"\",\n" +
                "                  \"link\": \"images/Tp6W0v39j5ZtT0Rpw1S0hrtmZ6ALoZRX.png\",\n" +
                "                  \"images_categories_id\": 116,\n" +
                "                  \"id\": 38488\n" +
                "                }\n" +
                "              ],\n" +
                "              \"word_id\": 40060098,\n" +
                "              \"text\": \"four\",\n" +
                "              \"audio\": [\n" +
                "                {\n" +
                "                  \"duration\": 1,\n" +
                "                  \"file_path\": \"TWqBaYDlMxxNwpC0sXxpSMfJDrwyyZcx.mp3\",\n" +
                "                  \"voices_type_id\": 34,\n" +
                "                  \"tag_title\": \"four\",\n" +
                "                  \"link\": \"audio/TWqBaYDlMxxNwpC0sXxpSMfJDrwyyZcx.mp3\",\n" +
                "                  \"name_original\": \"four-male\",\n" +
                "                  \"voices_id\": 34,\n" +
                "                  \"id\": 75441,\n" +
                "                  \"sync_data\": \"\"\n" +
                "                }\n" +
                "              ],\n" +
                "              \"type\": \"question\",\n" +
                "              \"order\": 0\n" +
                "            },\n" +
                "            {\n" +
                "              \"path\": \"e3c0338f-a696-442b-a95f-535f2e22c6f6_265876.zip\",\n" +
                "              \"image\": [],\n" +
                "              \"word_id\": 40060099,\n" +
                "              \"text\": \"four\",\n" +
                "              \"audio\": [\n" +
                "                {\n" +
                "                  \"duration\": 0,\n" +
                "                  \"file_path\": \"040ebc82-ca3c-4254-a2a0-3d4f2b4720f0.mp3\",\n" +
                "                  \"voices_type_id\": 67,\n" +
                "                  \"tag_title\": \"four\",\n" +
                "                  \"link\": \"audio/040ebc82-ca3c-4254-a2a0-3d4f2b4720f0.mp3\",\n" +
                "                  \"name_original\": \"\",\n" +
                "                  \"voices_id\": 67,\n" +
                "                  \"id\": 166053,\n" +
                "                  \"sync_data\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                  \"duration\": 0,\n" +
                "                  \"file_path\": \"e77dd810-497a-4156-b701-9a25e9e09b11.mp3\",\n" +
                "                  \"voices_type_id\": 68,\n" +
                "                  \"tag_title\": \"four\",\n" +
                "                  \"link\": \"audio/e77dd810-497a-4156-b701-9a25e9e09b11.mp3\",\n" +
                "                  \"name_original\": \"\",\n" +
                "                  \"voices_id\": 68,\n" +
                "                  \"id\": 166054,\n" +
                "                  \"sync_data\": \"\"\n" +
                "                }\n" +
                "              ],\n" +
                "              \"type\": \"question_answer\",\n" +
                "              \"order\": 0\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"right_answer\": \"\",\n" +
                "          \"word\": [\n" +
                "            {\n" +
                "              \"path\": \"3c90af7f-7e16-44d8-8dca-a6a1694b3058_265877.zip\",\n" +
                "              \"image\": [\n" +
                "                {\n" +
                "                  \"file_path\": \"XwYSPLuPKGsKZIlz6GYrOSkWpstRo6vu.png\",\n" +
                "                  \"file_type\": \"\",\n" +
                "                  \"link\": \"images/XwYSPLuPKGsKZIlz6GYrOSkWpstRo6vu.png\",\n" +
                "                  \"images_categories_id\": 116,\n" +
                "                  \"id\": 38487\n" +
                "                }\n" +
                "              ],\n" +
                "              \"word_id\": 40060100,\n" +
                "              \"text\": \"five\",\n" +
                "              \"audio\": [\n" +
                "                {\n" +
                "                  \"duration\": 0,\n" +
                "                  \"file_path\": \"DujmYoxO175xFl5UEFBCFMeKe8OaHr0P.mp3\",\n" +
                "                  \"voices_type_id\": 34,\n" +
                "                  \"tag_title\": \"five\",\n" +
                "                  \"link\": \"audio/DujmYoxO175xFl5UEFBCFMeKe8OaHr0P.mp3\",\n" +
                "                  \"name_original\": \"five-male\",\n" +
                "                  \"voices_id\": 34,\n" +
                "                  \"id\": 75440,\n" +
                "                  \"sync_data\": \"\"\n" +
                "                }\n" +
                "              ],\n" +
                "              \"type\": \"question\",\n" +
                "              \"order\": 0\n" +
                "            },\n" +
                "            {\n" +
                "              \"path\": \"55405b3d-4207-4ecd-aab6-de667f8278df_265878.zip\",\n" +
                "              \"image\": [],\n" +
                "              \"word_id\": 40060101,\n" +
                "              \"text\": \"five\",\n" +
                "              \"audio\": [\n" +
                "                {\n" +
                "                  \"duration\": 0,\n" +
                "                  \"file_path\": \"5ff810c8-bcd2-4cbc-94a5-68e43f5f96eb.mp3\",\n" +
                "                  \"voices_type_id\": 67,\n" +
                "                  \"tag_title\": \"five\",\n" +
                "                  \"link\": \"audio/5ff810c8-bcd2-4cbc-94a5-68e43f5f96eb.mp3\",\n" +
                "                  \"name_original\": \"\",\n" +
                "                  \"voices_id\": 67,\n" +
                "                  \"id\": 166055,\n" +
                "                  \"sync_data\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                  \"duration\": 0,\n" +
                "                  \"file_path\": \"79fd834a-f170-4e0a-aeb9-308bb05c1df6.mp3\",\n" +
                "                  \"voices_type_id\": 68,\n" +
                "                  \"tag_title\": \"five\",\n" +
                "                  \"link\": \"audio/79fd834a-f170-4e0a-aeb9-308bb05c1df6.mp3\",\n" +
                "                  \"name_original\": \"\",\n" +
                "                  \"voices_id\": 68,\n" +
                "                  \"id\": 166056,\n" +
                "                  \"sync_data\": \"\"\n" +
                "                }\n" +
                "              ],\n" +
                "              \"type\": \"question_answer\",\n" +
                "              \"order\": 0\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ],\n" +
                "      \"game_id\": 1000084\n" +
                "    },\n" +
                "    {\n" +
                "      \"game_name\": \"Chunking1Speaking(Clone)\",\n" +
                "      \"file_zip\": \"App/zip/activity/1503142-43045-rvl.zip\",\n" +
                "      \"download_error\": \"success\",\n" +
                "      \"turn\": [\n" +
                "        {\n" +
                "          \"right_answer\": \"\",\n" +
                "          \"word\": [\n" +
                "            {\n" +
                "              \"path\": \"8ec8b906-38c6-4bc3-beac-cb62c34a3ff8_265881.zip\",\n" +
                "              \"image\": [],\n" +
                "              \"word_id\": 40060104,\n" +
                "              \"text\": \"I'm 4 years old.\",\n" +
                "              \"audio\": [\n" +
                "                {\n" +
                "                  \"duration\": 0,\n" +
                "                  \"file_path\": \"25f818ce-9123-4e72-ba49-5605162029d3.mp3\",\n" +
                "                  \"voices_type_id\": 65,\n" +
                "                  \"tag_title\": \"I'm 4 years old.\",\n" +
                "                  \"link\": \"audio/25f818ce-9123-4e72-ba49-5605162029d3.mp3\",\n" +
                "                  \"name_original\": \"\",\n" +
                "                  \"voices_id\": 65,\n" +
                "                  \"id\": 166059,\n" +
                "                  \"sync_data\": [\n" +
                "                    {\n" +
                "                      \"te\": 2,\n" +
                "                      \"s\": 0,\n" +
                "                      \"e\": 390,\n" +
                "                      \"w\": \"I'm\",\n" +
                "                      \"ts\": 0\n" +
                "                    },\n" +
                "                    {\n" +
                "                      \"te\": 4,\n" +
                "                      \"s\": 390,\n" +
                "                      \"e\": 580,\n" +
                "                      \"w\": \"4\",\n" +
                "                      \"ts\": 4\n" +
                "                    },\n" +
                "                    {\n" +
                "                      \"te\": 10,\n" +
                "                      \"s\": 580,\n" +
                "                      \"e\": 1000,\n" +
                "                      \"w\": \"years\",\n" +
                "                      \"ts\": 6\n" +
                "                    },\n" +
                "                    {\n" +
                "                      \"te\": 15,\n" +
                "                      \"s\": 1000,\n" +
                "                      \"e\": 1560,\n" +
                "                      \"w\": \"old.\",\n" +
                "                      \"ts\": 12\n" +
                "                    }\n" +
                "                  ]\n" +
                "                }\n" +
                "              ],\n" +
                "              \"type\": \"question\",\n" +
                "              \"order\": 0\n" +
                "            },\n" +
                "            {\n" +
                "              \"path\": \"0d9494df-91ea-43be-9611-8d2fbe269709_265984.zip\",\n" +
                "              \"image\": [],\n" +
                "              \"word_id\": 40060102,\n" +
                "              \"text\": \"I'm\",\n" +
                "              \"audio\": [\n" +
                "                {\n" +
                "                  \"duration\": 1,\n" +
                "                  \"file_path\": \"u2KE4sgUDuolEHSHPxqTJNdmnO6FRRxd.mp3\",\n" +
                "                  \"voices_type_id\": 1,\n" +
                "                  \"tag_title\": \"\",\n" +
                "                  \"link\": \"audio/u2KE4sgUDuolEHSHPxqTJNdmnO6FRRxd.mp3\",\n" +
                "                  \"name_original\": \"I'm\",\n" +
                "                  \"voices_id\": \"65\",\n" +
                "                  \"id\": 166142,\n" +
                "                  \"sync_data\": []\n" +
                "                }\n" +
                "              ],\n" +
                "              \"type\": \"chunk\",\n" +
                "              \"order\": 1\n" +
                "            },\n" +
                "            {\n" +
                "              \"path\": \"093c7cc5-8d85-437c-ae66-09694bc05679_265985.zip\",\n" +
                "              \"image\": [],\n" +
                "              \"word_id\": 40060103,\n" +
                "              \"text\": \"4 years old\",\n" +
                "              \"audio\": [\n" +
                "                {\n" +
                "                  \"duration\": 1,\n" +
                "                  \"file_path\": \"vRHnWu3bsL04baW4mJeGkIN3c9umfuhT.mp3\",\n" +
                "                  \"voices_type_id\": 1,\n" +
                "                  \"tag_title\": \"\",\n" +
                "                  \"link\": \"audio/vRHnWu3bsL04baW4mJeGkIN3c9umfuhT.mp3\",\n" +
                "                  \"name_original\": \"4 years old\",\n" +
                "                  \"voices_id\": \"65\",\n" +
                "                  \"id\": 166143,\n" +
                "                  \"sync_data\": [\n" +
                "                    {\n" +
                "                      \"te\": 0,\n" +
                "                      \"s\": 0,\n" +
                "                      \"e\": 540,\n" +
                "                      \"w\": \"4\",\n" +
                "                      \"ts\": 0\n" +
                "                    },\n" +
                "                    {\n" +
                "                      \"te\": 6,\n" +
                "                      \"s\": 540,\n" +
                "                      \"e\": 880,\n" +
                "                      \"w\": \"years\",\n" +
                "                      \"ts\": 2\n" +
                "                    },\n" +
                "                    {\n" +
                "                      \"te\": 10,\n" +
                "                      \"s\": 880,\n" +
                "                      \"e\": 1360,\n" +
                "                      \"w\": \"old\",\n" +
                "                      \"ts\": 8\n" +
                "                    }\n" +
                "                  ]\n" +
                "                }\n" +
                "              ],\n" +
                "              \"type\": \"chunk\",\n" +
                "              \"order\": 2\n" +
                "            },\n" +
                "            {\n" +
                "              \"path\": \"8ec8b906-38c6-4bc3-beac-cb62c34a3ff8_265881.zip\",\n" +
                "              \"image\": [],\n" +
                "              \"word_id\": 40060104,\n" +
                "              \"text\": \"I'm 4 years old.\",\n" +
                "              \"audio\": [\n" +
                "                {\n" +
                "                  \"duration\": 0,\n" +
                "                  \"file_path\": \"25f818ce-9123-4e72-ba49-5605162029d3.mp3\",\n" +
                "                  \"voices_type_id\": 65,\n" +
                "                  \"tag_title\": \"I'm 4 years old.\",\n" +
                "                  \"link\": \"audio/25f818ce-9123-4e72-ba49-5605162029d3.mp3\",\n" +
                "                  \"name_original\": \"\",\n" +
                "                  \"voices_id\": 65,\n" +
                "                  \"id\": 166059,\n" +
                "                  \"sync_data\": [\n" +
                "                    {\n" +
                "                      \"te\": 2,\n" +
                "                      \"s\": 0,\n" +
                "                      \"e\": 390,\n" +
                "                      \"w\": \"I'm\",\n" +
                "                      \"ts\": 0\n" +
                "                    },\n" +
                "                    {\n" +
                "                      \"te\": 4,\n" +
                "                      \"s\": 390,\n" +
                "                      \"e\": 580,\n" +
                "                      \"w\": \"4\",\n" +
                "                      \"ts\": 4\n" +
                "                    },\n" +
                "                    {\n" +
                "                      \"te\": 10,\n" +
                "                      \"s\": 580,\n" +
                "                      \"e\": 1000,\n" +
                "                      \"w\": \"years\",\n" +
                "                      \"ts\": 6\n" +
                "                    },\n" +
                "                    {\n" +
                "                      \"te\": 15,\n" +
                "                      \"s\": 1000,\n" +
                "                      \"e\": 1560,\n" +
                "                      \"w\": \"old.\",\n" +
                "                      \"ts\": 12\n" +
                "                    }\n" +
                "                  ]\n" +
                "                }\n" +
                "              ],\n" +
                "              \"type\": \"chunk\",\n" +
                "              \"order\": 3\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"right_answer\": \"\",\n" +
                "          \"word\": [\n" +
                "            {\n" +
                "              \"path\": \"02438abf-7785-4d49-9b51-e59b3c086824_265884.zip\",\n" +
                "              \"image\": [],\n" +
                "              \"word_id\": 40060107,\n" +
                "              \"text\": \"Nice to meet you, too.\",\n" +
                "              \"audio\": [\n" +
                "                {\n" +
                "                  \"duration\": 0,\n" +
                "                  \"file_path\": \"c79a67c6-6928-41f8-a95a-cf18f80569a8.mp3\",\n" +
                "                  \"voices_type_id\": 65,\n" +
                "                  \"tag_title\": \"Nice to meet you, too.\",\n" +
                "                  \"link\": \"audio/c79a67c6-6928-41f8-a95a-cf18f80569a8.mp3\",\n" +
                "                  \"name_original\": \"\",\n" +
                "                  \"voices_id\": 65,\n" +
                "                  \"id\": 166062,\n" +
                "                  \"sync_data\": [\n" +
                "                    {\n" +
                "                      \"te\": 3,\n" +
                "                      \"s\": 0,\n" +
                "                      \"e\": 490,\n" +
                "                      \"w\": \"Nice\",\n" +
                "                      \"ts\": 0\n" +
                "                    },\n" +
                "                    {\n" +
                "                      \"te\": 6,\n" +
                "                      \"s\": 490,\n" +
                "                      \"e\": 640,\n" +
                "                      \"w\": \"to\",\n" +
                "                      \"ts\": 5\n" +
                "                    },\n" +
                "                    {\n" +
                "                      \"te\": 11,\n" +
                "                      \"s\": 640,\n" +
                "                      \"e\": 900,\n" +
                "                      \"w\": \"meet\",\n" +
                "                      \"ts\": 8\n" +
                "                    },\n" +
                "                    {\n" +
                "                      \"te\": 16,\n" +
                "                      \"s\": 900,\n" +
                "                      \"e\": 1240,\n" +
                "                      \"w\": \"you,\",\n" +
                "                      \"ts\": 13\n" +
                "                    },\n" +
                "                    {\n" +
                "                      \"te\": 21,\n" +
                "                      \"s\": 1240,\n" +
                "                      \"e\": 1840,\n" +
                "                      \"w\": \"too.\",\n" +
                "                      \"ts\": 18\n" +
                "                    }\n" +
                "                  ]\n" +
                "                }\n" +
                "              ],\n" +
                "              \"type\": \"question\",\n" +
                "              \"order\": 0\n" +
                "            },\n" +
                "            {\n" +
                "              \"path\": \"a6bfa5b8-f113-4fd4-8a43-f06fb73c5fbf_265986.zip\",\n" +
                "              \"image\": [],\n" +
                "              \"word_id\": 40060105,\n" +
                "              \"text\": \"to meet\",\n" +
                "              \"audio\": [\n" +
                "                {\n" +
                "                  \"duration\": 1,\n" +
                "                  \"file_path\": \"8U98hAymNYLa9LkmDlM3PFodOd00ap1Y.mp3\",\n" +
                "                  \"voices_type_id\": 1,\n" +
                "                  \"tag_title\": \"\",\n" +
                "                  \"link\": \"audio/8U98hAymNYLa9LkmDlM3PFodOd00ap1Y.mp3\",\n" +
                "                  \"name_original\": \"to meet\",\n" +
                "                  \"voices_id\": \"65\",\n" +
                "                  \"id\": 166141,\n" +
                "                  \"sync_data\": [\n" +
                "                    {\n" +
                "                      \"te\": 1,\n" +
                "                      \"s\": 0,\n" +
                "                      \"e\": 520,\n" +
                "                      \"w\": \"to\",\n" +
                "                      \"ts\": 0\n" +
                "                    },\n" +
                "                    {\n" +
                "                      \"te\": 6,\n" +
                "                      \"s\": 520,\n" +
                "                      \"e\": 1100,\n" +
                "                      \"w\": \"meet\",\n" +
                "                      \"ts\": 3\n" +
                "                    }\n" +
                "                  ]\n" +
                "                }\n" +
                "              ],\n" +
                "              \"type\": \"chunk\",\n" +
                "              \"order\": 1\n" +
                "            },\n" +
                "            {\n" +
                "              \"path\": \"5237b4b9-4288-4459-9a8e-21f75cd51c58_265987.zip\",\n" +
                "              \"image\": [],\n" +
                "              \"word_id\": 40060106,\n" +
                "              \"text\": \"Nice to meet\",\n" +
                "              \"audio\": [\n" +
                "                {\n" +
                "                  \"duration\": 2,\n" +
                "                  \"file_path\": \"TGxJoLorg1Ni94zMBFraTxsJ2zrnFZ5O.mp3\",\n" +
                "                  \"voices_type_id\": 1,\n" +
                "                  \"tag_title\": \"\",\n" +
                "                  \"link\": \"audio/TGxJoLorg1Ni94zMBFraTxsJ2zrnFZ5O.mp3\",\n" +
                "                  \"name_original\": \"Nice to meet\",\n" +
                "                  \"voices_id\": \"65\",\n" +
                "                  \"id\": 166140,\n" +
                "                  \"sync_data\": [\n" +
                "                    {\n" +
                "                      \"te\": 3,\n" +
                "                      \"s\": 0,\n" +
                "                      \"e\": 760,\n" +
                "                      \"w\": \"Nice\",\n" +
                "                      \"ts\": 0\n" +
                "                    },\n" +
                "                    {\n" +
                "                      \"te\": 6,\n" +
                "                      \"s\": 760,\n" +
                "                      \"e\": 1120,\n" +
                "                      \"w\": \"to\",\n" +
                "                      \"ts\": 5\n" +
                "                    },\n" +
                "                    {\n" +
                "                      \"te\": 11,\n" +
                "                      \"s\": 1120,\n" +
                "                      \"e\": 1530,\n" +
                "                      \"w\": \"meet\",\n" +
                "                      \"ts\": 8\n" +
                "                    }\n" +
                "                  ]\n" +
                "                }\n" +
                "              ],\n" +
                "              \"type\": \"chunk\",\n" +
                "              \"order\": 2\n" +
                "            },\n" +
                "            {\n" +
                "              \"path\": \"02438abf-7785-4d49-9b51-e59b3c086824_265884.zip\",\n" +
                "              \"image\": [],\n" +
                "              \"word_id\": 40060107,\n" +
                "              \"text\": \"Nice to meet you, too.\",\n" +
                "              \"audio\": [\n" +
                "                {\n" +
                "                  \"duration\": 0,\n" +
                "                  \"file_path\": \"c79a67c6-6928-41f8-a95a-cf18f80569a8.mp3\",\n" +
                "                  \"voices_type_id\": 65,\n" +
                "                  \"tag_title\": \"Nice to meet you, too.\",\n" +
                "                  \"link\": \"audio/c79a67c6-6928-41f8-a95a-cf18f80569a8.mp3\",\n" +
                "                  \"name_original\": \"\",\n" +
                "                  \"voices_id\": 65,\n" +
                "                  \"id\": 166062,\n" +
                "                  \"sync_data\": [\n" +
                "                    {\n" +
                "                      \"te\": 3,\n" +
                "                      \"s\": 0,\n" +
                "                      \"e\": 490,\n" +
                "                      \"w\": \"Nice\",\n" +
                "                      \"ts\": 0\n" +
                "                    },\n" +
                "                    {\n" +
                "                      \"te\": 6,\n" +
                "                      \"s\": 490,\n" +
                "                      \"e\": 640,\n" +
                "                      \"w\": \"to\",\n" +
                "                      \"ts\": 5\n" +
                "                    },\n" +
                "                    {\n" +
                "                      \"te\": 11,\n" +
                "                      \"s\": 640,\n" +
                "                      \"e\": 900,\n" +
                "                      \"w\": \"meet\",\n" +
                "                      \"ts\": 8\n" +
                "                    },\n" +
                "                    {\n" +
                "                      \"te\": 16,\n" +
                "                      \"s\": 900,\n" +
                "                      \"e\": 1240,\n" +
                "                      \"w\": \"you,\",\n" +
                "                      \"ts\": 13\n" +
                "                    },\n" +
                "                    {\n" +
                "                      \"te\": 21,\n" +
                "                      \"s\": 1240,\n" +
                "                      \"e\": 1840,\n" +
                "                      \"w\": \"too.\",\n" +
                "                      \"ts\": 18\n" +
                "                    }\n" +
                "                  ]\n" +
                "                }\n" +
                "              ],\n" +
                "              \"type\": \"chunk\",\n" +
                "              \"order\": 3\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ],\n" +
                "      \"game_id\": 1000101\n" +
                "    }\n" +
                "  ],\n" +
                "  \"level\": \"A\",\n" +
                "  \"lesson\": \"Lesson 2\",\n" +
                "  \"topic\": \"Hello!\",\n" +
                "  \"category\": \"Greetings 1\"\n" +
                "}";
        String value = JsonHandle.getValue(json,"$.length($.act[?(@.game_name==\"Chunking1Speaking(Clone)\")].turn[1].word[?(@.type=='chunk')].length())");
        System.out.println(value);
    }*/
}
