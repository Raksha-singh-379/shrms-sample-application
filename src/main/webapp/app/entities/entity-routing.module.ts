import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'employee',
        data: { pageTitle: 'shrmsApp.employee.home.title' },
        loadChildren: () => import('./employee/employee.module').then(m => m.EmployeeModule),
      },
      {
        path: 'employee-details',
        data: { pageTitle: 'shrmsApp.employeeDetails.home.title' },
        loadChildren: () => import('./employee-details/employee-details.module').then(m => m.EmployeeDetailsModule),
      },
      {
        path: 'address',
        data: { pageTitle: 'shrmsApp.address.home.title' },
        loadChildren: () => import('./address/address.module').then(m => m.AddressModule),
      },
      {
        path: 'family-info',
        data: { pageTitle: 'shrmsApp.familyInfo.home.title' },
        loadChildren: () => import('./family-info/family-info.module').then(m => m.FamilyInfoModule),
      },
      {
        path: 'contacts',
        data: { pageTitle: 'shrmsApp.contacts.home.title' },
        loadChildren: () => import('./contacts/contacts.module').then(m => m.ContactsModule),
      },
      {
        path: 'banks-details',
        data: { pageTitle: 'shrmsApp.banksDetails.home.title' },
        loadChildren: () => import('./banks-details/banks-details.module').then(m => m.BanksDetailsModule),
      },
      {
        path: 'education',
        data: { pageTitle: 'shrmsApp.education.home.title' },
        loadChildren: () => import('./education/education.module').then(m => m.EducationModule),
      },
      {
        path: 'work-experience',
        data: { pageTitle: 'shrmsApp.workExperience.home.title' },
        loadChildren: () => import('./work-experience/work-experience.module').then(m => m.WorkExperienceModule),
      },
      {
        path: 'projects',
        data: { pageTitle: 'shrmsApp.projects.home.title' },
        loadChildren: () => import('./projects/projects.module').then(m => m.ProjectsModule),
      },
      {
        path: 'project-teams',
        data: { pageTitle: 'shrmsApp.projectTeams.home.title' },
        loadChildren: () => import('./project-teams/project-teams.module').then(m => m.ProjectTeamsModule),
      },
      {
        path: 'emp-salary-info',
        data: { pageTitle: 'shrmsApp.empSalaryInfo.home.title' },
        loadChildren: () => import('./emp-salary-info/emp-salary-info.module').then(m => m.EmpSalaryInfoModule),
      },
      {
        path: 'company',
        data: { pageTitle: 'shrmsApp.company.home.title' },
        loadChildren: () => import('./company/company.module').then(m => m.CompanyModule),
      },
      {
        path: 'region',
        data: { pageTitle: 'shrmsApp.region.home.title' },
        loadChildren: () => import('./region/region.module').then(m => m.RegionModule),
      },
      {
        path: 'branch',
        data: { pageTitle: 'shrmsApp.branch.home.title' },
        loadChildren: () => import('./branch/branch.module').then(m => m.BranchModule),
      },
      {
        path: 'state',
        data: { pageTitle: 'shrmsApp.state.home.title' },
        loadChildren: () => import('./state/state.module').then(m => m.StateModule),
      },
      {
        path: 'district',
        data: { pageTitle: 'shrmsApp.district.home.title' },
        loadChildren: () => import('./district/district.module').then(m => m.DistrictModule),
      },
      {
        path: 'taluka',
        data: { pageTitle: 'shrmsApp.taluka.home.title' },
        loadChildren: () => import('./taluka/taluka.module').then(m => m.TalukaModule),
      },
      {
        path: 'city',
        data: { pageTitle: 'shrmsApp.city.home.title' },
        loadChildren: () => import('./city/city.module').then(m => m.CityModule),
      },
      {
        path: 'salary-settings',
        data: { pageTitle: 'shrmsApp.salarySettings.home.title' },
        loadChildren: () => import('./salary-settings/salary-settings.module').then(m => m.SalarySettingsModule),
      },
      {
        path: 'department',
        data: { pageTitle: 'shrmsApp.department.home.title' },
        loadChildren: () => import('./department/department.module').then(m => m.DepartmentModule),
      },
      {
        path: 'designation',
        data: { pageTitle: 'shrmsApp.designation.home.title' },
        loadChildren: () => import('./designation/designation.module').then(m => m.DesignationModule),
      },
      {
        path: 'leave-type',
        data: { pageTitle: 'shrmsApp.leaveType.home.title' },
        loadChildren: () => import('./leave-type/leave-type.module').then(m => m.LeaveTypeModule),
      },
      {
        path: 'leave-policy',
        data: { pageTitle: 'shrmsApp.leavePolicy.home.title' },
        loadChildren: () => import('./leave-policy/leave-policy.module').then(m => m.LeavePolicyModule),
      },
      {
        path: 'leave-application',
        data: { pageTitle: 'shrmsApp.leaveApplication.home.title' },
        loadChildren: () => import('./leave-application/leave-application.module').then(m => m.LeaveApplicationModule),
      },
      {
        path: 'leave-transcation',
        data: { pageTitle: 'shrmsApp.leaveTranscation.home.title' },
        loadChildren: () => import('./leave-transcation/leave-transcation.module').then(m => m.LeaveTranscationModule),
      },
      {
        path: 'holidays',
        data: { pageTitle: 'shrmsApp.holidays.home.title' },
        loadChildren: () => import('./holidays/holidays.module').then(m => m.HolidaysModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
