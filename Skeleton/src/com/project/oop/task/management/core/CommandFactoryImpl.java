//package com.project.oop.task.management.core;
//
//import com.project.oop.task.management.commands.AddCommentToTaskCommand;
//import com.project.oop.task.management.commands.AddPersonToTeamCommand;
//import com.project.oop.task.management.commands.AssignTaskCommand;
//import com.project.oop.task.management.commands.UnassignTaskCommand;
//import com.project.oop.task.management.commands.change.*;
//import com.project.oop.task.management.commands.contracts.Command;
//import com.project.oop.task.management.commands.creation.*;
//import com.project.oop.task.management.commands.enums.CommandType;
//import com.project.oop.task.management.commands.listing.*;
//import com.project.oop.task.management.commands.show.*;
//import com.project.oop.task.management.core.contracts.CommandFactory;
//import com.project.oop.task.management.utils.ParsingHelpers;
//
//public class CommandFactoryImpl implements CommandFactory {
//
//    @Override
//    public Command createCommandFromCommandName(String commandTypeAsString, TaskManagementRepositoryImpl taskManagementRepository) {
//        CommandType commandType = ParsingHelpers.tryParseEnum(commandTypeAsString, CommandType.class);
//        switch (commandType) {
//            case CHANGEBUGPRIORITY:
//                return new ChangeBugPriorityCommand(taskManagementRepository);
//            case CHANGEBUGSEVERITY:
//                return new ChangeBugSeverityCommand(taskManagementRepository);
//            case CHANGEBUGSTATUS:
//                return new ChangeBugStatusCommand(taskManagementRepository);
//            case CHANGEFEEDBACKRATING:
//                return new ChangeFeedbackRatingCommand(taskManagementRepository);
//            case CHANGEFEEDBACKSTATUS:
//                return new ChangeFeedbackStatusCommand(taskManagementRepository);
//            case CHANGESTORYPRIORITY:
//                return new ChangeStoryPriorityCommand(taskManagementRepository);
//            case CHANGESTORYSIZE:
//                return new ChangeStorySizeCommand(taskManagementRepository);
//            case CHANGESTORYSTATUS:
//                return new ChangeStoryStatusCommand(taskManagementRepository);
//            case CREATENEWBOARD:
//                return new CreateNewBoardCommand(taskManagementRepository);
//            case CREATENEWBUG:
//                return new CreateNewBugCommand(taskManagementRepository);
//            case CREATENEWPERSON:
//                return new CreateNewPersonCommand(taskManagementRepository);
//            case CREATENEWFEEDBACK:
//                return new CreateNewFeedbackCommand(taskManagementRepository);
//            case CREATENEWSTORY:
//                return new CreateNewStoryCommand(taskManagementRepository);
//            case CREATENEWTEAM:
//                return new CreateNewTeamCommand(taskManagementRepository);
//            case FILTERBUGSBYASSIGNEE:
//                return new FilterBugsByAssigneeCommand(taskManagementRepository);
//            case FILTERBUGSBYSTATUS:
//                return new FilterBugsByStatusCommand(taskManagementRepository);
//            case FILTERFEEDBACKBYASSIGNEE:
//                return new FilterFeedbackByAssigneeCommand(taskManagementRepository);
//            case FILTERFEEDBACKBYSTATUS:
//                return new FilterFeedbackByStatusCommand(taskManagementRepository);
//            case FILTERSTORIESBYASSIGNEE:
//                return new FilterStoriesByAssigneeCommand(taskManagementRepository);
//            case FILTERSTORIESBYSTATUS:
//                return new FilterStoriesByStatusCommand(taskManagementRepository);
//            case FILTERTASKSBYTITLE:
//                return new FilterTasksByTitleCommand(taskManagementRepository);
//            case FILTERTASKSWITHASSIGNEEBYASSIGNEE:
//                return new FilterTasksWithAssigneeByAssigneeCommand(taskManagementRepository);
//            case FILTERTASKSWITHASSIGNEEBYSTATUS:
//                return new FilterTasksWithAssigneeByStatusCommand(taskManagementRepository);
//            case LISTBUGS:
//                return new ListBugsCommand(taskManagementRepository);
//            case LISTFEEDBACK:
//                return new ListFeedbackCommand(taskManagementRepository);
//            case LISTSTORIES:
//                return new ListStoriesCommand(taskManagementRepository);
//            case LISTTASKS:
//                return new ListTasksCommand(taskManagementRepository);
//            case LISTTASKSWITHASSIGNEE:
//                return new ListTasksWithAssigneeCommand(taskManagementRepository);
//            case SORTBUGSBYPRIORITY:
//                return new SortBugsByPriorityCommand(taskManagementRepository);
//            case SORTBUGSBYSEVERITY:
//                return new SortBugsBySeverityCommand(taskManagementRepository);
//            case SORTBUGSBYTITLE:
//                return new SortBugsByTitleCommand(taskManagementRepository);
//            case SORTFEEDBACKBYTITLE:
//                return new SortFeedbackByTitleCommand(taskManagementRepository);
//            case SORTFEEDBACKBYRATING:
//                return new SortFeedbackByRatingCommand(taskManagementRepository);
//            case SORTSTORIESBYPRIORITY:
//                return new SortStoriesByPriorityCommand(taskManagementRepository);
//            case SORTSTORIESBYSIZE:
//                return new SortStoriesBySizeCommand(taskManagementRepository);
//            case SORTSTORIESBYTITLE:
//                return new SortStoriesByTitleCommand(taskManagementRepository);
//            case SORTTASKSBYTITLE:
//                return new SortTasksByTitleCommand(taskManagementRepository);
//            case SORTTASKSWITHASSIGNEEBYTITLE:
//                return new SortTasksWithAssigneeByTitleCommand(taskManagementRepository);
//            case SHOWALLPEOPLE:
//                return new ShowAllPeopleCommand(taskManagementRepository);
//            case SHOWALLTEAMBOARDS:
//                return new ShowAllTeamBoardsCommand(taskManagementRepository);
//            case SHOWALLTEAMMEMBERS:
//                return new ShowAllTeamMembersCommand(taskManagementRepository);
//            case SHOWALLTEAMS:
//                return new ShowAllTeamsCommand(taskManagementRepository);
//            case SHOWBOARDACTIVITY:
//                return new ShowBoardActivityCommand(taskManagementRepository);
//            case SHOWPERSONACTIVITY:
//                return new ShowPersonActivityCommand(taskManagementRepository);
//            case SHOWTEAMACTIVITY:
//                return new ShowTeamActivityCommand(taskManagementRepository);
//            case ADDCOMMENTTOTASK:
//                return new AddCommentToTaskCommand(taskManagementRepository);
//            case ADDPERSONTOTEAM:
//                return new AddPersonToTeamCommand(taskManagementRepository);
//            case ASSIGNTASK:
//                return new AssignTaskCommand(taskManagementRepository);
//            case UNASSIGNTASK:
//                return new UnassignTaskCommand(taskManagementRepository);
//            case FILTERBUGSBYSTATUSANDASSIGNEE:
//                return new FilterBugsByStatusAndAssigneeCommand(taskManagementRepository);
//            case FILTERFEEDBACKBYSTATUSANDASSIGNEE:
//                return new FilterFeedbackByStatusAndAssigneeCommand(taskManagementRepository);
//            case FILTERSTORIESBYSTATUSANDASSIGNEE:
//                return new FilterStoriesByStatusAndAssigneeCommand(taskManagementRepository);
//            case FILTERTASKSWITHASSIGNEEBYSTATUSANDASSIGNEE:
//                return new FilterTasksWithAssigneeByStatusAndAssigneeCommand(taskManagementRepository);
//            default:
//                throw new IllegalArgumentException();
//        }
//    }
//}
